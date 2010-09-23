#! /bin/sh
# Script to create SD card for OMAPL138 plaform.
#
# Author: Brijesh Singh, Texas Instruments Inc.
#
# Licensed under terms of GPLv2

VERSION="0.3"

execute ()
{
    $* >/dev/null
    if [ $? -ne 0 ]; then
        echo
        echo "ERROR: executing $*"
        echo
        exit 1
    fi
}

version ()
{
  echo
  echo "`basename $1` version $VERSION"
  echo "Script to create bootable SD card for OMAPL138 EVM"
  echo

  exit 0
}

usage ()
{
  echo "
Usage: `basename $1` [options] <sdk_install_dir> <device>

  --device              SD block device node (e.g /dev/sdd)
  --sdk                 Where is sdk installed ?
  --copy                Copy file on third partition.
  --version             Print version.
  --help                Print this help message.
"
  exit 1
}

# Process command line...
while [ $# -gt 0 ]; do
  case $1 in
    --help | -h)
      usage $0
      ;;
    --device) shift; device=$1; shift; ;;
    --sdk) shift; sdkdir=$1; shift; ;;
    --copy) shift; copy=$1; shift; ;;
    --version) version $0;;
    *) usage $0;;
  esac
done

test -z $sdkdir && usage $0
test -z $device && usage $0

if [ ! -d $sdkdir ]; then
   echo "ERROR: $sdkdir does not exist"
   exit 1;
fi

if [ ! -f $sdkdir/filesystem/dvsdk-da850-omapl138-evm-rootfs.tar.gz ]; then
  echo "ERROR: failed to find rootfs $sdkdir/filesystem/dvsdk-da850-omapl138-evm-rootfs.tar.gz"
  exit 1;
fi
 
if [ ! -b $device ]; then
   echo "ERROR: $device is not a block device file"
   exit 1;
fi

if [ "$copy" != "" ]; then
  if [ ! -f $copy ]; then
    echo "ERROR: $copy does not exist"
    exit 1
  fi
fi

echo "************************************************************"
echo "*         THIS WILL DELETE ALL THE DATA ON $device        *"
echo "*                                                          *"
echo "*         WARNING! Make sure your computer does not go     *"
echo "*                  in to idle mode while this script is    *"
echo "*                  running. The script will complete,      *"
echo "*                  but your SD card may be corrupted.      *"
echo "*                                                          *"
echo "*         Press <ENTER> to confirm....                     *"
echo "************************************************************"
read junk

for i in `ls -1 $device?`; do
 echo "unmounting device '$i'"
 umount $i 2>/dev/null
done

execute "dd if=/dev/zero of=$device bs=1024 count=1024"

# get the partition information.
total_size=`fdisk -l $device | grep Disk | awk '{print $5}'`
total_cyln=`echo $total_size/255/63/512 | bc`

# start from cylinder 20, this should give enough space for flashing utility
# to write u-boot binary. 
pc1_start=20
pc1_end=10

# start of rootfs partition
pc2_start=$(($pc1_start + $pc1_end))

# calculate number of cylinder for the second parition
if [ "$copy" != "" ]; then
 pc2_end=$((($total_cyln - $pc1_end) / 2))
 pc3_start=$(($pc2_start + $pc2_end))
fi

{
if [ "$copy" != "" ]; then
 echo $pc1_start,$pc1_end,0x0C,-
 echo $pc2_start,$pc2_end,,-
 echo $pc3_start,,-
else
 echo $pc1_start,$pc1_end,,-
 echo $pc2_start,,-
fi
} | sfdisk -D -H 255 -S 63 -C $total_cyln $device

if [ $? -ne 0 ]; then
    echo ERROR
    exit 1;
fi

echo "Formating ${device}1 ..."
execute "mkfs.vfat -F 32 -n "BOOT" ${device}1"
echo "Formating ${device}2 ..."
execute "mke2fs -j -L "ROOTFS" ${device}2"
if [ "$copy" != "" ]; then
 echo "Formating ${device}3 ..."
 execute "mke2fs -j -L "START_HERE" ${device}3"
fi

# creating boot.scr
execute "mkdir -p /tmp/sdk"
cat <<EOF >/tmp/sdk/boot.cmd
mmc init
setenv bootargs 'console=ttyS2,115200n8 root=/dev/mmcblk0p2 rw ip=off mem=32M rootwait'
fatload mmc 0 c0700000 uImage
bootm c0700000
EOF

mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n 'Execute uImage' -d /tmp/sdk/boot.cmd /tmp/sdk/boot.scr

if [ $? -ne 0 ]; then
  echo "Failed to execute mkimage to create boot.scr"
  echo "Execute 'sudo apt-get install uboot-mkimage' to install the package"
  exit 1
fi

echo "Copying uImage/boot.scr on ${device}1"
execute "mkdir -p /tmp/sdk/$$"
execute "mount ${device}1 /tmp/sdk/$$"
execute "cp /tmp/sdk/boot.scr /tmp/sdk/$$/"
execute "cp /tmp/sdk/boot.cmd /tmp/sdk/$$/"
execute "cp $sdkdir/psp/prebuilt-images/uImage /tmp/sdk/$$"
execute "cp $sdkdir/bin/top_omapl138_evm.png /tmp/sdk/$$/"
execute "cp $sdkdir/bin/windows_users.htm /tmp/sdk/$$/"
execute "cp $sdkdir/bin/README.boot.scr /tmp/sdk/$$/"
execute "umount /tmp/sdk/$$"

echo "Executing uflash tool to write u-boot.bin"
$sdkdir/psp/board-utilities/uflash/uflash -d ${device} -b ${sdkdir}/psp/prebuilt-images/u-boot-mmc.bin -p omapl138 -vv

if [ $? -ne 0 ]; then
  echo "Failed to execute uflash"
  exit 1
fi

echo "Extracting filesystem on ${device}2 ..."
execute "mkdir -p /tmp/sdk/$$"
execute "mount ${device}2 /tmp/sdk/$$"
execute "tar zxf $sdkdir/filesystem/dvsdk-da850-omapl138-evm-rootfs.tar.gz -C /tmp/sdk/$$"

# check if we need to create symbolic link for matrix 
echo -n "Creating matrix-gui symbolic link..."
if [ -f /tmp/sdk/$$/etc/init.d/matrix-gui ]; then
  if [ -h /tmp/sdk/$$/etc/rc3.d/*matrix* ]; then
    echo " (skipped) "
  else
    ln -s  ../init.d/matrix-gui /tmp/sdk/$$/etc/rc3.d/S99matrix-gui
    ln -s  ../init.d/matrix-gui /tmp/sdk/$$/etc/rc5.d/S99matrix-gui
    echo "done"
  fi
fi

echo "unmounting ${device}2"
execute "umount /tmp/sdk/$$"

if [ "$copy" != "" ]; then
  echo "Copying $copy on ${device}3 ..."
  execute "mount ${device}3 /tmp/sdk/$$"
  execute "cp -ar $copy /tmp/sdk/$$"
  execute "cp $sdkdir/bin/setup.htm /tmp/sdk/$$"
  execute "cp $sdkdir/bin/top_omapl138x_evm.png /tmp/sdk/$$/"
  execute "cp $sdkdir/docs/OMAPL138_EVM_Quick_Start_Guide.pdf /tmp/sdk/$$/quickstartguide.pdf"
  echo "unmounting ${device}3"
  execute "umount /tmp/sdk/$$"
fi

execute "rm -rf /tmp/sdk/$$"
echo "completed!"

