#! /usr/bin/env bash
#
#  (C) Copyright 2010 Texas Instrument Inc.  All rights reserved.
#
#  Author: Brijesh Singh, Texas Instrunents Inc.
#
#  Script to install TI SDK
#

VERSION="1.1"
DVSDK_VERSION="__<version>__"

#
# Display program usage
#
usage()
{
  echo "
Usage: `basename $1` [options] --machine <machine_name> <install_dir>

  --help                Print this help message
  --graphics            Install graphics packages
  --bsp                 Install board support packages
  --dsp                 Install c64p dsp packages
  --multimedia          Install multimedia packages
  --force               Force installation on unsupported host
"
  exit 1
}

#
# Display version 
#
version()
{
  echo "
`basename $1`: version $VERSION 
"
  exit 1
}

#
# verify if cdrom has valid arch.conf and sdktools ipk
#
verify_cdrom ()
{
  echo "Verifying CDROM ..."
  if [ ! -f config/$machine/opkg.conf ]; then
    echo "ERROR: opkg.conf does not exist for $machine"
    exit 1;
  fi
  sed s=\${PWD}=$PWD/deploy= config/dm365-evm/opkg.conf > ${install_dir}/.opkg.conf
  opkg_conf="${install_dir}/.opkg.conf"
}

#
# execute command
#
execute ()
{
  echo "$*"
  $*
  if [ $? -ne 0 ]; then
    echo "ERROR: failed to execute $*"
    exit 1;
  fi
}

#
# Extract tar balls from BSP directory
#
extract_tars()
{
  echo "Extracting Linux kernel source tar ... "
  # extract lsp source
  if [ -f deploy/ipk/$machine/linux-*staging*.tar.gz ]; then
    mkdir -p $install_dir/psp/linux-kernel-source
    lsp="`ls -1 deploy/ipk/$machine/linux-*staging*.tar.gz`"
    execute "tar zxf ${lsp}  -C $install_dir/psp/linux-kernel-source"
  else
    echo "ERROR: failed to find linux kernel tarball"
    exit 1
  fi

  # extract linuxlibs 
  echo "Extracting linuxlibs tar ..."
  if [ ! -f devel/linuxlibs*.tar.gz ]; then
    echo "ERROR: failed to find linuxlibs tarball"
    exit 1
  fi
  linuxlibs="`ls -1 devel/linuxlibs*.tar.gz`"
  execute "tar zxf ${linuxlibs} -C ${install_dir}"
  mv ${install_dir}/linuxlibs* ${install_dir}/linuxlibs

}

#
# update sdk Rules.make
#
update_rules_make()
{
  echo "Updating Rules.make ..."
  for i in ${install_dir}/usr/lib/opkg/info/*sourcetree*.control; do
    # we are not greping package name because the name contains ti-*-sourcetree
    name="`cat $i | grep OE | awk {'print $2'} | cut -f2-5 -d-`"

    # in some case version will be 1:xxxx and we need to remove 1: part 
    version="`cat $i | grep Version | awk {'print $2'} | cut -f2 -d: | \
          cut -f1 -d-`"

    # update rules.make
    sed -i -e s/\<__${name}__\>/${name}_${version}/g \
     $install_dir/usr/share/ti/Rules.make
  done

  sed -i -e s=\<__kernel__\>=psp/linux-kernel-source/git= \
    $install_dir/usr/share/ti/Rules.make
  sed -i -e s=\<__SDK__INSTALL_DIR__\>=${install_dir}= \
    $install_dir/usr/share/ti/Rules.make
}

#
# move sourcetree in top label directory to give dvsdk finishing
#
move_to_install_dir()
{
  echo "Moving sourcetree ..."
  for i in ${install_dir}/usr/lib/opkg/info/*.control; do
    # we are not greping package name because the name contains ti-*-sourcetree
    name="`cat $i | grep OE | awk {'print $2'} | cut -f2-5 -d-`"

    # in some case version will be 1:xxxx and we need to remove 1: part 
    version="`cat $i | grep Version | awk {'print $2'} | cut -f2 -d: | \
          cut -f1 -d-`"

    # move source from pkginstall_dir/ti-$name-tree to 
    # $ROOT_DIR/$name_$version to present older dvsdk style
    if [ -d ${install_dir}/usr/share/ti/ti-$name-tree ]; then
      echo " from ti-$name-tree => ${name}_${version}"
      mv ${install_dir}/usr/share/ti/ti-$name-tree ${install_dir}/${name}_${version}
    fi

    # Handle the special case, where codec source directory is named as  
    # "ti-codecs-tree" instead of ti-codecs-$machine-tree. 
    case "$name" in
      codecs-*) 
        echo " from ti-codecs-tree => ${name}_${version}"
        mv ${install_dir}/usr/share/ti/ti-codecs-tree ${install_dir}/${name}_${version}
        ;;
    esac

  done
  if [ -d ${install_dir}/usr/share/ti/ti-psp-tree ]; then
    echo " from ti-psp-tree => psp"
    mv ${install_dir}/usr/share/ti/ti-psp-tree/* ${install_dir}/psp
    rm -rf ${install_dir}/usr/share/ti/ti-psp-tree
  fi

  mv ${install_dir}/usr/share/ti/* ${install_dir}/
  rm -rf $install_dir/usr
  rm -rf $install_dir/lib
  rm -rf $install_dir/etc
  rm -rf $install_dir/sbin
}

#
# Generate software manifest file
#
generate_sw_manifest()
{
if [ ! -d $2/usr/lib/opkg/info  ]; then
  return;
fi

echo "
<h2><u>$1</u></h2>
<table border=1 cellspacing=1 cellpadding=1 width=80%>
<tr bgcolor=#c0c0c0  color=white><td><b>Package Name</b></td><td><b>Version</b></td><td><b>License</b></td><td><b>Location</b></td><td><b>Delivered As</b></td> <td><b>Modified</b></td><td><b>Obtained from</b></td></tr>
"

  for i in $2/usr/lib/opkg/info/*.control; do
    package="`cat $i | grep Package: | awk {'print $2'}`"
    version="`cat $i | grep Version: | awk {'print $2'} | cut -f1-2 -d-`"
    license="`cat $i | grep License: | awk {'print $2,$3,$4'} `"
    source="`cat $i | grep Source: | awk {'print $2'}`"
    case "$license" in 
      *unknown*) highlight="bgcolor=yellow" ;;
      *GPLv3*) highlight="bgcolor=yellow" ;;
      *) highlight="" ;;
    esac

    case "$source" in
      file://*) source="";;
      *) ;;
    esac

    case "$package" in
      task-*) continue ;;
      ti-*-sourcetree*) delivered_as="Source and Binary"
            modified="Yes" 
            location="$2"
            ;; 
      ti-*) delivered_as="Source and Binary"
            location="$2"
            modified="Yes" ;; 
      *)    delivered_as="Binary"
            location="$2"
            modified="No";;
            
    esac

    echo "
<tr><td>${package} </td><td>${version}</td> <td $highlight> ${license} </td><td>$location</td><td>$delivered_as</td><td>$modified</td> <td><a href=$source>$source</a></td></tr>
"
  done
echo "</table><br><br>"
}

#
# software manifest header
# 
sw_manifest_header()
{
echo "
<HTML>
<HEAD>
<TITLE>
$machine DVSDK ${DVSDK_VERSION} Installation Summary 
</TITLE>
</HEAD>
<BODY>
<h1><CENTER> $machine DVSDK ${DVSDK_VERSION} Software Manifest </CENTER></h1>
<h2><b><u>Legend</u></b></h2>
<table border=1 width=45%>
<tr><td>Package Name</td><td>The name of the application or files</td></tr>
<tr><td>Version</td><td>Version of the application or files</td></tr>
<tr><td>License</td><td>Name of the license or licenses that apply to the Package.</td></tr>
<tr><td>Location</td><td>The directory name and path on the media (or in an archive) where the Package is located.</td></tr>
<tr><td>Delivered As</td><td>This field will either be &ldquo;Source&rdquo;, &ldquo;Binary&rdquo; or &ldquo;Source and Binary&ldquo; and is the form the content of the Package is delivered in.  If the Package is delivered in an archive format, this field applies to the contents of the archive. If the word Limited is used with Source, as in &ldquo;Limited Source&rdquo; or &ldquo;Limited Source and Binary&rdquo; then only portions of the Source for the application are provided.</td></tr>
<tr><td>Modified </td><td>This field will either be &ldquo;Yes&rdquo; or &ldquo;No&rdquo;. A &ldquo;Yes&rdquo; means TI had made changes to the Package. A &ldquo;No&rdquo; means TI has not made any changes.</td></tr>
<tr><td>Obtained from</td><td>This field specifies where TI obtained the Package from. It may be a URL to an Open Source site, a 3rd party company name or TI. If this field contains a link to an Open Source package, the date it was downloaded is also recorded.</td></tr>
</table>
"
}

#
# sw manifest footer
#
sw_manifest_footer()
{
echo "
<hr>
<right> <i><font color=grey>Auto generated by TI DVSDK installer on `date`</i> </right></font>
</body>
</html>
"
}

# 
# check supported host
#
host_check ()
{
  echo "Checking host ..."
  if [ "$force_host" = "yes" ]; then
    echo "Forcing installation on unsupported host"
  else
    lsb_release -a > /tmp/.log
    if [ $? -ne 0 ]; then
      echo "Unsupported host machine"
      exit 1;
    fi
    host="`cat /tmp/.log | grep Codename: | awk {'print $2'}`"
    if [ "$host" != "hardy" ]; then
      echo "Unsupported host machine" ;
      exit 1;
    fi
    echo " Found Ubuntu 8.04"
  fi
}

#
# prepare installer tools - this requires building opkg and fakeroot natively
# 
prepare_install ()
{
  echo "Preparing installation ..."

  cwd=$PWD;
  if [ ! -f install-tools.tgz ]; then
    echo "ERROR: failed to find install-tools.tgz"
    exit 1;
  fi

  execute "mkdir -p ${install_dir}"
  execute "tar zxf install-tools.tgz -C ${install_dir}"
  if [ ! -d ${install_dir}/install-tools/ ]; then
    echo "ERROR: failed to find installation tool"
    exit 1;
  fi

  execute "cd ${install_dir}/install-tools/"
  execute "tar zxf fakeroot*.tar.gz"
  execute "tar zxf opkg-src*.tar.gz"
  execute "cd fakeroot*"
  execute "./configure --prefix=${install_dir}/install-tools"
  execute "make"
  execute "make install"
  execute "cd ../"
  execute "cd opkg*"
  execute "./autogen.sh --disable-curl --disable-gpg --prefix=${install_dir}/install-tools"
  execute "make"
  execute "make install"
  export LD_LIBRARY_PATH=${install_dir}/install-tools/lib:$LD_LIBRARY_PATH
  export PATH=${install_dir}/install-tools/bin:$PATH
  cd $cwd
}

#
# install sourcetree or devel packages on host
#
host_install ()
{
  echo "Installing packages on host ..."
  mkdir -p ${install_dir}/usr/lib/opkg

  execute "opkg-cl --cache $install_dir/deploy/cache -o $install_dir -f ${opkg_conf}  update"
  execute "opkg-cl --cache $install_dir/deploy/cache -o $install_dir -f ${opkg_conf} install  $bsp_src $dsp_src $multimedia_src ti-tisdk-makefile"

  # extract kernel and linuxlibs header tarballs
  test ! -z $bsp_src && extract_tars

  # TODO: figure out a ways to remove glibc, libasound depedency from 
  # sourcetree packages.  For now uninstall those extra packages
  execute "opkg-cl  --cache ${install_dir}/deploy/cache -o ${install_dir} -f ${opkg_conf} remove  -force-depends libc6 libgcc1 libstdc++6 libasound2 alsa-conf-base sln"

  update_rules_make

  generate_sw_manifest "Packages installed on the host machine:" "$install_dir" >> ${install_dir}/docs/software_manifest.htm;

  move_to_install_dir
}

# Process command line...
while [ $# -gt 0 ]; do
  case $1 in
    --help | -h)
      usage $0
      ;;
    --version | -v)
      version $0
      ;;
    --graphics)
      graphics_src="task-arago-tisdk-graphics-host";
      graphics_bin="task-arago-tisdk-graphics-target";
      graphics="yes";
      shift;
      ;;
    --bsp)
      bsp_src="task-arago-tisdk-bsp-host";
      bsp_bin="task-arago-tisdk-bsp-target";
      bsp="yes";
      shift;
      ;;
    --dsp)
      dsp_src="task-arago-tisdk-dsp-host";
      dsp_bin="task-arago-tisdk-dsp-host";
      dsp="yes";
      shift;
      ;;
    --multimedia)
      multimedia_src="task-arago-tisdk-multimedia-host"
      multimedia_bin="task-arago-tisdk-multimedia-target"
      multimedia="yes";
      shift;
      ;;
    --machine)
      shift
      machine=$1;
      shift;
      ;;
    --force)
      shift
      force_host="yes";
      ;;
     *)
      install_dir=$1;
      shift;
      ;;
  esac
done

# check if machine is defined.
test -z $machine && usage $0

# check if install_dir variable is set.
test -z $install_dir && usage $0
execute "mkdir -p $install_dir"

# verify if cdrom has architecture specific opkg.conf.
verify_cdrom

# prepare installation.
prepare_install

# create software manifest header.
mkdir -p $install_dir/docs
sw_manifest_header > ${install_dir}/docs/software_manifest.htm

# install sourcetree (or devel) ipk on host.
host_install

# install binary ipk on target.
echo "Installing packages on target filesystem"
mkdir -p ${install_dir}/filesystem
cp deploy/images/$machine/* ${install_dir}/filesystem
fakeroot ./install_rootfs.sh $install_dir $bsp_bin $multimedia_bin $graphics_bin

tar zxf `ls -1 ${install_dir}/filesystem/*` -C $install_dir/filesystem --wildcards *.control*
generate_sw_manifest "Packages installed on the target:" "$install_dir/filesystem" >> ${install_dir}/docs/software_manifest.htm
rm -rf ${install_dir}/filesystem/usr

# add manifest footer.
sw_manifest_footer >> ${install_dir}/docs/software_manifest.htm

rm -rf ${opkg_conf}
rm -rf ${install_dir}/install-tools

echo "Installation completed!"

exit 0
 
