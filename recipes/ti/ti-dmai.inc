DESCRIPTION = "Davinci Multimedia Application Interface (DMAI) for TI ARM/DSP processors"
HOMEPAGE = "https://gforge.ti.com/gf/project/dmai/"
SECTION = "multimedia"
LICENSE = "BSD"

# TODO :: 

require ti-paths.inc
require ti-staging.inc

PROVIDES += "ti-dmai-apps"

PE = "1"

SRCREV               ?= "<UNDEFINED_SRCREV>"
DMAIBRANCH           ?= "<UNDEFINED_DMAIBRANCH>"

S = "${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/dmai_${PV}"

SRC_URI = "svn://gforge.ti.com/svn/dmai/;module=${DMAIBRANCH};proto=https;user=anonymous;pswd='' \
	    file://doxygen_templates.tar.gz \
        file://arago-tdox \
	"

DEPENDS = "virtual/kernel alsa-lib ti-framework-components ti-codec-engine ti-xdctools"

DEPENDS_append_dm6446 	= " ti-codecs-dm6446   ti-dspbios ti-cgt6x ti-linuxutils"
DEPENDS_append_dm6467 	= " ti-codecs-dm6467   ti-dspbios ti-cgt6x ti-linuxutils"
DEPENDS_append_omap3    = " ti-codecs-omap3530 ti-dspbios ti-cgt6x ti-linuxutils ti-c6accel"
DEPENDS_append_dm355  	= " ti-codecs-dm355"
DEPENDS_append_dm365    = " ti-codecs-dm365"
DEPENDS_append_omapl137 = " ti-codecs-omapl137 ti-dspbios ti-cgt6x ti-linuxutils"
DEPENDS_append_omapl138 = " ti-codecs-omapl138 ti-dspbios ti-cgt6x ti-linuxutils ti-c6accel"

# Define DMAI build time variables
DMAIPLATFORM_dm6446    = "dm6446_al"
DMAIPLATFORM_dm6467    = "dm6467_al"
DMAIPLATFORM_omap3     = "o3530_al"
DMAIPLATFORM_dm355     = "dm355_al"
DMAIPLATFORM_dm365     = "dm365_al"
DMAIPLATFORM_omapl137  = "ol137_al"
DMAIPLATFORM_omapl138  = "ol138_al"
DMAIPLATFORM          ?= "<UNDEFINED_DMAIPLATFORM>"

# Need to set this for other platforms as well
#GPPOS_dm355      = "LINUX_GCC"
#GPPOS_dm365      = "LINUX_GCC"
#GPPOS           ?= "<UNDEFINEDGPPOS>"

# Need to re-define this for OMAP-L137/L138
DSPSUFFIX_omapl137 = "x64P"
DSPSUFFIX_omapl138 = "x64P"
DSPSUFFIX ?= "x64p"

# This is needed for dm355/dm365 targets in order to find ti.sdo.codecs.g711
# TODO :: review - should we just pass this in do_compile?
USER_XDC_PATH = "${CE_INSTALL_DIR}/examples"

PARALLEL_MAKE = ""

do_prepsources() {
    # run the release steps - this will delete .svn file and run doxygen to
    # generate proper documentation of the source etc.
    find ${WORKDIR}/${DMAIBRANCH} -name .svn -type d | xargs rm -rf
    cp -pPrf ${WORKDIR}/doxygen_templates ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface
    cp -pPrf ${WORKDIR}/arago-tdox ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/tdox
    chmod a+x ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/release.sh
    chmod a+x ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/tdox
    sed -i 's|tdox|./tdox|g' ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/release.sh
    (cd ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface; ./release.sh ${PV})
    rm -rf ${WORKDIR}/${DMAIBRANCH}/davinci_multimedia_application_interface/dmai_${PV}/wince_pb
}

# run prepare sources before do_patch to ensure that sourceipk picks the right sources.
addtask prepsources after do_unpack before do_patch

do_compile () {
    # TODO :: Why do we do this?
    unset DMAI_INSTALL_DIR
    cd ${S}

    make XDC_INSTALL_DIR="${XDC_INSTALL_DIR}" PLATFORM="${DMAIPLATFORM}" clean

    for dir in ${S}; do
        cd $dir
        #  TODO: Figure out how to pass the alsa require location, currently 
        #  LINUXLIBS_INSTALL_DIR is hard-coded for armv5te
        make \
            CE_INSTALL_DIR="${CE_INSTALL_DIR}" \
            CODEC_INSTALL_DIR="${CODEC_INSTALL_DIR}" \
            FC_INSTALL_DIR="${FC_INSTALL_DIR}" \
            LINUXKERNEL_INSTALL_DIR="${STAGING_KERNEL_DIR}" \
            XDC_INSTALL_DIR="${XDC_INSTALL_DIR}" \
            CODEGEN_INSTALL_DIR="${CODEGEN_INSTALL_DIR}" \
            BIOS_INSTALL_DIR="${BIOS_INSTALL_DIR}"\
            LINUXLIBS_INSTALL_DIR="${STAGING_DIR_TARGET}/usr" \
            USER_XDC_PATH="${USER_XDC_PATH}" \
            CROSS_COMPILE="${TOOLCHAIN_PATH}/bin/${TARGET_PREFIX}" \
            VERBOSE="true" \
            XDAIS_INSTALL_DIR="${XDAIS_INSTALL_DIR}" \
            LINK_INSTALL_DIR="${LINK_INSTALL_DIR}" \
            CMEM_INSTALL_DIR="${CMEM_INSTALL_DIR}" \
            LPM_INSTALL_DIR="${LPM_INSTALL_DIR}" \	
            MVTOOL_PREFIX="${TARGET_PREFIX}" \
	        C6ACCEL_INSTALL_DIR="${C6ACCEL_INSTALL_DIR}" \
            PLATFORM="${DMAIPLATFORM}" 
	done
}

do_install () {

    # TODO :: Why do we do this?
    unset DMAI_INSTALL_DIR

    install -d ${D}/${installdir}/ti-dmai-apps
    cd ${S}
    make PLATFORM="${DMAIPLATFORM}" EXEC_DIR=${D}/${installdir}/ti-dmai-apps install 

    install -d ${D}${DMAI_INSTALL_DIR_RECIPE}
    cp -pPrf ${S}/* ${D}${DMAI_INSTALL_DIR_RECIPE}
}

PACKAGES += "ti-dmai-apps"
FILES_ti-dmai-apps = "${installdir}/ti-dmai-apps/*"
INSANE_SKIP_ti-dmai-apps = True

RDEPENDS_ti-dmai-apps_dm6446    += "ti-codecs-dm6446-server   ti-cmem-module ti-dsplink-module"
RDEPENDS_ti-dmai-apps_dm6467    += "ti-codecs-dm6467          ti-cmem-module ti-dsplink-module"
RDEPENDS_ti-dmai-apps_omap3     += "ti-codecs-omap3530-server ti-cmem-module ti-dsplink-module ti-lpm-module ti-sdma-module ti-tisdk-loadmodule"
RDEPENDS_ti-dmai-apps_dm355     += "ti-codecs-dm355           ti-cmem-module ti-dm355mm-module"
RDEPENDS_ti-dmai-apps_dm365     += "ti-codecs-dm365           ti-cmem-module ti-dm365mm-module ti-edma-module ti-irq-module ti-tisdk-loadmodule"
RDEPENDS_ti-dmai-apps_omapl137  += "ti-codecs-omapl137-server ti-cmem-module ti-dsplink-module"
RDEPENDS_ti-dmai-apps_omapl138  += "ti-codecs-omapl138-server ti-cmem-module ti-dsplink-module ti-tisdk-loadmodule"

pkg_postinst_ti-dmai-apps () {
	ln -sf ${installdir}/ti-codecs-server/*.${DSPSUFFIX} ${installdir}/ti-dmai-apps/
}

