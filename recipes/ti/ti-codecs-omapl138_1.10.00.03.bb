DESCRIPTION = "TI Codecs (and Server Combo) for OMAPL138"
HOMEPAGE = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent"
SECTION = "multimedia"
LICENSE = "TI"

# TODO :: Move to common .inc (omap3 and omapl ready)

PV = "1_10_00_03"

PR = "r0"

require ti-paths.inc
require ti-staging.inc

PROVIDES += "ti-codecs-omapl138-server"

S = "${WORKDIR}/cs1omapl138_${PV}"

SRC_URI = "ftp://nemo.sc.ti.com/pub/int-packages/cs1omapl138_${PV}.tar.gz;name=l138codecsbin"

SRC_URI[l138codecsbin.md5sum] = "91373bf8f8d2f8a0aa96d2df078b742c"
SRC_URI[l138codecsbin.sha256sum] = "bb74aadbdc3ad0752a9a45240cf613fdd7e9faeb1fd308d629436e85af95edaf"

DEPENDS = "ti-cgt6x ti-xdctools ti-dspbios ti-codec-engine ti-linuxutils ti-c6accel"

#generic codec
DSPSUFFIX_omapl138 = "x64P"

do_prepsources() {

	make \
             CE_INSTALL_DIR=${CE_INSTALL_DIR} \
             FC_INSTALL_DIR=${FC_INSTALL_DIR} \
             LINK_INSTALL_DIR=${LINK_INSTALL_DIR} \
             CMEM_INSTALL_DIR=${CMEM_INSTALL_DIR} \
             LPM_INSTALL_DIR=${LPM_INSTALL_DIR} \
             BIOS_INSTALL_DIR=${BIOS_INSTALL_DIR} \
             CODEGEN_INSTALL_DIR=${CODEGEN_INSTALL_DIR} \
             XDC_INSTALL_DIR=${XDC_INSTALL_DIR} \
             CODEC_INSTALL_DIR="${S}" \
             XDCARGS="prod" \
			 C6ACCEL_INSTALL_DIR=${C6ACCEL_INSTALL_DIR}/soc \
             clean
}

addtask prepsources after do_configure before do_compile

do_compile() {

	make \
             CE_INSTALL_DIR=${CE_INSTALL_DIR} \
             FC_INSTALL_DIR=${FC_INSTALL_DIR} \
             LINK_INSTALL_DIR=${LINK_INSTALL_DIR} \
             CMEM_INSTALL_DIR=${CMEM_INSTALL_DIR} \
             LPM_INSTALL_DIR=${LPM_INSTALL_DIR} \
             BIOS_INSTALL_DIR=${BIOS_INSTALL_DIR} \
             CODEGEN_INSTALL_DIR=${CODEGEN_INSTALL_DIR} \
             XDC_INSTALL_DIR=${XDC_INSTALL_DIR} \
             CODEC_INSTALL_DIR="${S}" \
             XDCARGS="prod" \
			 C6ACCEL_INSTALL_DIR=${C6ACCEL_INSTALL_DIR}/soc \
             all
}

do_install() {

    install -d ${D}/${installdir}/ti-codecs-server
    cd ${S}

    # Install the DSP Server Binary 
    for file in `find . -name *.${DSPSUFFIX}`; do
        cp ${file} ${D}/${installdir}/ti-codecs-server
    done

    # Install docs (codec qualiTI test reports, server config datasheet, etc)
    for file in `find . -name *.html`; do
        cp ${file} ${D}/${installdir}/ti-codecs-server
    done

    install -d ${D}${CODEC_INSTALL_DIR_RECIPE}
    cp -pPrf ${S}/* ${D}${CODEC_INSTALL_DIR_RECIPE}
}

PACKAGES += "ti-codecs-omapl138-server"
FILES_ti-codecs-omapl138-server = "${installdir}/ti-codecs-server/*"


