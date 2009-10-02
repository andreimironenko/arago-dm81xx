DESCRIPTION = "This package creates Rules.make file and copies top label Makefile for rebuilding dvsdk components."

inherit sdk

PR="r8"

FILES=${@os.path.dirname(bb.data.getVar('FILE',d,1))}/files/dvsdk-rules

DEPENDS  = "ti-codecs-dm355-sdk ti-codecs-dm365-sdk ti-dm365mm-module-sdk ti-xdctools-sdk ti-xdais-sdk ti-codec-engine-sdk ti-linuxutils-sdk ti-framework-components-sdk ti-dmai-sdk ti-dvsdk-demos-sdk"
DEPENDS_dm6446-evm  = "ti-codec-combo-dm6446-sdk  ti-cgt6x-sdk ti-dspbios-sdk ti-xdctools-sdk ti-xdais-sdk ti-codec-engine-sdk ti-linuxutils-sdk ti-framework-components-sdk ti-dmai-sdk"
DEPENDS_omap3evm    = "ti-codec-combo-omap3530-sdk ti-cgt6x-sdk ti-dspbios-sdk ti-xdctools-sdk ti-xdais-sdk ti-codec-engine-sdk ti-linuxutils-sdk ti-framework-components-sdk ti-dmai-sdk"
DEPENDS_beagleboard = "ti-codec-combo-omap3530-sdk ti-cgt6x-sdk ti-dspbios-sdk ti-xdctools-sdk ti-xdais-sdk ti-codec-engine-sdk ti-linuxutils-sdk ti-framework-components-sdk ti-dmai-sdk"

include ../ti-multimedia-common.inc

do_install () {
    mkdir -p  ${D}/${prefix}/dvsdk/

    # Create Rules.make file by concatinating pkg Rules.make files.
    echo "# Define target platform." > ${D}/${prefix}/dvsdk/Rules.make
    echo "PLATFORM=${PLATFORM}" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "# The installation directory of the DVSDK." >> ${D}/${prefix}/dvsdk/Rules.make
    echo "DVSDK_INSTALL_DIR=${prefix}/dvsdk" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "# For backwards compatibility" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "DVEVM_INSTALL_DIR=\$(DVSDK_INSTALL_DIR)" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
  
    for file in `ls -1 ${STAGING_DIR_HOST}/ti-sdk-rules` ; do
      cat ${STAGING_DIR_HOST}/ti-sdk-rules/${file} >> ${D}/${prefix}/dvsdk/Rules.make
      echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    done

    echo "# The directory that points to your kernel source directory." >>  ${D}/${prefix}/dvsdk/Rules.make
    echo "LINUXKERNEL_INSTALL_DIR=${prefix}/${TARGET_SYS}/usr/src/kernel" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "# Where temporary Linux headers and libs are installed." >>  ${D}/${prefix}/dvsdk/Rules.make
    echo "LINUXLIBS_INSTALL_DIR=${prefix}/${TARGET_SYS}/usr" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "# The prefix to be added before the GNU compiler tools (optionally including # path), i.e. \"arm_v5t_le-\" or \"/opt/bin/arm_v5t_le-\"." >>  ${D}/${prefix}/dvsdk/Rules.make 
    echo "CSTOOL_DIR=${TOOLCHAIN_PATH}" >>  ${D}/${prefix}/dvsdk/Rules.make
    echo "CSTOOL_PREFIX=\$(CSTOOL_DIR)/bin/arm-none-linux-gnueabi-" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "MVTOOL_DIR=\$(CSTOOL_DIR)" >>  ${D}/${prefix}/dvsdk/Rules.make
    echo "MVTOOL_PREFIX=\$(CSTOOL_PREFIX)" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "" >> ${D}/${prefix}/dvsdk/Rules.make
    echo "# Where to copy the resulting executables" >>  ${D}/${prefix}/dvsdk/Rules.make
    echo "EXEC_DIR=\$(HOME)/install/\$(PLATFORM)" >>  ${D}/${prefix}/dvsdk/Rules.make

    # copy Makefile and other scripts needed by Makefile
    mkdir -p ${D}/${prefix}/dvsdk/bin
    cp ${FILES}/Makefile ${D}/${prefix}/dvsdk/
    cp ${FILES}/info.sh  ${D}/${prefix}/dvsdk/bin
    cp ${FILES}/check.sh ${D}/${prefix}/dvsdk/bin
    mkdir -p ${D}/${prefix}/dvsdk/mapdmaq
    install -m 0755 ${FILES}/../mapdmaq ${D}/${prefix}/dvsdk/mapdmaq
}

FILES_${PN} = "${prefix}/dvsdk/*"