#!/bin/sh

cwd=`dirname $0`
. $cwd/common.sh

echo
echo "--------------------------------------------------------------------------------"
echo "This step will set up the u-boot variables for booting the EVM."

SDKinstall=`grep TI_SDK_PATH= $cwd/../Rules.make | cut -d= -f2`

dstdefault=$SDKinstall/targetNFS


ipdefault=`ifconfig | grep 'inet addr:'| grep -v '127.0.0.1' | cut -d: -f2 | awk '{ print $1 }'`
platform=`grep PLATFORM= $cwd/../Rules.make | cut -d= -f2`
prompt=" >"

echo "Autodetected the following ip address of your host, correct it if necessary"
read -p "[ $ipdefault ] " ip
echo

if [ ! -n "$ip" ]; then
    ip=$ipdefault
fi

if [ -f $cwd/../.targetfs ]; then
    rootpath=`cat $cwd/../.targetfs`
else
    echo "Where is your target filesystem extracted?"
    read -p "[ $dstdefault ]" rootpath

    if [ ! -n "$rootpath" ]; then
        rootpath="$dstdefault"
    fi
    echo
fi

if [ -f $cwd/../.tftproot ]; then
    tftproot=`cat $cwd/../.tftproot`
else
    echo "Where is your TFTP root?"
    read -p "[ /tftpboot ] " tftproot

    if [ ! -n "$tftproot" ]; then
        tftproot="/tftpboot"
    fi
    echo
fi

uimage="uImage-""$platform"".bin"
uimagesrc=`ls -1 $cwd/../board-support/prebuilt-images/$uimage`
uimagedefault=`basename $uimagesrc`

baseargs="console=ttyS2,115200n8 rw noinitrd"
videoargs=""
extendbaseargs=""
fssdargs="root=/dev/mmcblk0p2 rootfstype=ext3 rootwait"
fsnfsargs1="root=/dev/nfs nfsroot="
fsnfsargs2="$ip:"
fsnfsargs3="$rootpath"
fsnfsargs4=",nolock,rsize=1024,wsize=1024"
fsnfsargs=$fsnfsargs1$fsnfsargs2$fsnfsargs3$fsnfsargs4

echo "Select Linux kernel location:"
echo " 1: TFTP"
echo " 2: SD card"
echo " 3: flash"
echo
read -p "[ 1 ] " kernel

if [ ! -n "$kernel" ]; then
    kernel="1"
fi

echo
echo "Select root file system location:"
echo " 1: NFS"
echo " 2: SD card"
echo
read -p "[ 1 ] " fs

if [ ! -n "$fs" ]; then
    fs="1"
fi

if [ "$kernel" -eq "1" ]; then
    echo
    echo "Available kernel images in $tftproot:"
    for file in $tftproot/*; do
        basefile=`basename $file`
        echo "    $basefile"
    done
    if [ ! -f $tftproot/$uimagedefault ]; then
        uimagedefault=`ls -1 $tftproot/* | head -1`
        uimagedefault=`basename $uimagedefault`
    fi
    echo
    echo "Which kernel image do you want to boot from TFTP?"
    read -p "[ $uimagedefault ] " uimage

    if [ ! -n "$uimage" ]; then
        uimage=$uimagedefault
    fi

#    bootcmd="setenv bootcmd 'dhcp;bootm'"
    bootcmd="setenv bootcmd 'dhcp;setenv serverip $ip;tftpboot;bootm'"
    serverip="setenv serverip $ip"
    bootfile="setenv bootfile $uimage"

    if [ "$fs" -eq "1" ]; then
        bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fsnfsargs ip=dhcp"
        cfg="uimage-tftp_fs-nfs"
    else
        bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fssdargs ip=off"
        cfg="uimage-tftp_fs-sd"
    fi
else
    if [ "$kernel" -eq "2" ]; then

        if [ "$fs" -eq "1" ]; then
            bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fsnfsargs ip=dhcp"
            bootcmd="setenv bootcmd 'mmc rescan 0; fatload mmc 0 0xc0700000 uImage; bootm 0xc0700000'"
            cfg="uimage-sd_fs-nfs"
        else
            bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fssdargs ip=off"
            bootcmd="setenv bootcmd 'mmc rescan 0; fatload mmc 0 0xc0700000 uImage; bootm 0xc0700000'"
            cfg="uimage-sd_fs-sd"
        fi
    else
        if [ "$fs" -eq "1" ]; then
            bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fsnfsargs ip=dhcp"
            bootcmd="setenv bootcmd 'sf probe 0; sf read 0xc0700000 0x80000 0x280000; bootm 0xc0700000'"
            cfg="uimage-flash_fs-nfs"
        else
            bootargs="setenv bootargs $baseargs $extendbaseargs $videoargs $fssdargs ip=off"
            bootcmd="setenv bootcmd 'sf probe 0; sf read 0xc0700000 0x80000 0x280000; bootm 0xc0700000'"
            cfg="uimage-flash_fs-sd"
	fi
    fi
fi

echo
echo "Resulting u-boot variable settings:"
echo
echo "setenv bootdelay 3"
echo "setenv baudrate 115200"
echo $bootargs
echo $bootcmd

if [ -n "$serverip" ]; then
    echo "setenv autoload no"
    echo $serverip
fi

if [ -n "$bootfile" ]; then
    echo $bootfile
fi
echo "--------------------------------------------------------------------------------"

do_expect() {
    echo "expect {" >> $3
    check_status
    echo "    $1" >> $3
    check_status
    echo "}" >> $3
    check_status
    echo $2 >> $3
    check_status
    echo >> $3
}

echo
echo "--------------------------------------------------------------------------------"
echo "Would you like to create a minicom script with the above parameters (y/n)?"
read -p "[ y ] " minicom
echo

if [ ! -n "$minicom" ]; then
    minicom="y"
fi

#Use = instead of == for POSIX compliance and dash compatibility
if [ "$minicom" = "y" ]; then
    minicomfile=setup_$platform_$cfg.minicom
    minicomfilepath=$cwd/../$minicomfile

    if [ -f $minicomfilepath ]; then
        echo "Moving existing $minicomfile to $minicomfile.old"
        mv $minicomfilepath $minicomfilepath.old
        check_status
    fi

    timeout=300
    echo "timeout $timeout" >> $minicomfilepath
    echo "verbose on" >> $minicomfilepath
    echo >> $minicomfilepath
    do_expect "\"stop autoboot:\"" "send \"\"" $minicomfilepath
    do_expect "\"$prompt\"" "send \"setenv bootdelay 4\"" $minicomfilepath
    do_expect "\"$prompt\"" "send \"setenv baudrate 115200\"" $minicomfilepath
    do_expect "\"ENTER ...\"" "send \"\"" $minicomfilepath
    do_expect "\"$prompt\"" "send \"setenv oldbootargs \$\{bootargs\}\"" $minicomfilepath

#   For dash compatibility need to use printf instead of echo
#   because dash shell will expand the \c by default
#   do_expect "\"$prompt\"" "send \"setenv bootargs $baseargs \c\"" $minicomfilepath
    echo "expect {" >> $minicomfilepath
    check_status
    echo "    \"$prompt\"" >> $minicomfilepath
    check_status
    echo "}" >> $minicomfilepath
    check_status
    printf "send \"setenv bootargs $baseargs \c\"\n" >> $minicomfilepath
    check_status

#   For dash compatibility need to use printf instead of echo
#   printf "send \"$extendbaseargs \c\"\n" >> $minicomfilepath
#   printf "send \"$videoargs1 \c\"\n" >> $minicomfilepath
#   printf "send \"$videoargs2 \c\"\n" >> $minicomfilepath
#   printf "send \"$videoargs3 \c\"\n" >> $minicomfilepath
    if [ "$fs" -eq "1" ]; then
        printf "send \"$fsnfsargs1\c\"\n" >> $minicomfilepath
        printf "send \"$fsnfsargs2\c\"\n" >> $minicomfilepath
        printf "send \"$fsnfsargs3\c\"\n" >> $minicomfilepath
        printf "send \"$fsnfsargs4 \c\"\n" >> $minicomfilepath
        echo "send \"ip=dhcp\"" >> $minicomfilepath
    else
        printf "send \"$fssdargs \c\"\n" >> $minicomfilepath
        echo "send \"ip=off\"" >> $minicomfilepath
    fi
    if [ "$kernel" -eq "1" ]; then
        do_expect "\"$prompt\"" "send \"setenv autoload no\"" $minicomfilepath
        do_expect "\"$prompt\"" "send \"setenv oldserverip \$\{serverip\}\"" $minicomfilepath
        do_expect "\"$prompt\"" "send \"$serverip\"" $minicomfilepath
#        do_expect "\"$prompt\"" "send \"setenv oldbootfile \$\{bootfile\}\"" $minicomfilepath
        do_expect "\"$prompt\"" "send \"$bootfile\"" $minicomfilepath
    fi
    do_expect "\"$prompt\"" "send \"setenv oldbootcmd \$\{bootcmd\}\"" $minicomfilepath
    do_expect "\"$prompt\"" "send \"$bootcmd\"" $minicomfilepath
    do_expect "\"$prompt\"" "send \"saveenv\"" $minicomfilepath
    do_expect "\"$prompt\"" "! killall -s SIGHUP minicom" $minicomfilepath

    echo -n "Successfully wrote "
    readlink -m $minicomfilepath

    echo
    echo "Would you like to run the setup script now (y/n)? This requires you to connect"
    echo "the RS-232 cable between your host and EVM as well as your ethernet cable as"
    echo "described in the Quick Start Guide. Once answering 'y' on the prompt below"
    echo "you will have $timeout seconds to connect the board and power cycle it"
    echo "before the setup times out."
    echo
    echo "After successfully executing this script, your EVM will be set up. You will be "
    echo "able to connect to it by executing 'minicom -w' or if you prefer a windows host"
    echo "you can set up Tera Term as explained in the Software Developer's Guide."
    echo "If you connect minicom or Tera Term and power cycle the board Linux will boot."
    echo
    read -p "[ y ] " minicomsetup

    if [ ! -n "$minicomsetup" ]; then
        minicomsetup="y"
    fi

    savedir=""
    #Use = instead of == for POSIX compliance and dash compatibility
    if [ "$minicomsetup" = "y" ]; then
        #For dash compatibility, do not use pushd & popd
        savedir=$cwd
  	cd "$cwd/.."
        check_status
        minicom -S $minicomfile
        cd $savedir
        check_status
    fi
    
    echo "You can manually run minicom in the future with this setup script using: minicom -S $minicomfile"
fi
echo "--------------------------------------------------------------------------------"
