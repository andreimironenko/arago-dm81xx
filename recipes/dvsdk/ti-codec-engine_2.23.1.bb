DESCRIPTION = "Codec Engine 2.23.01 for TI ARM/DSP processors"

# compile time dependencies
DEPENDS_dm6446-evm 	+= "ti-xdctools ti-cgt6x ti-dspbios ti-codec-combo-dm6446"
DEPENDS_omap3evm   	+= "ti-cgt6x ti-dspbios ti-codec-combo-omap3530 ti-xdctools"
DEPENDS_beagleboard	+= "ti-cgt6x ti-dspbios ti-codec-combo-omap3530 ti-xdctools"
DEPENDS_dm355-evm 	+= "ti-codec-combo-dm355 ti-xdctools"
PREFERRED_VERSION_ti-dspbios	= "533"
PREFERRED_VERSION_ti-cgt6x  	= "60"
PREFERRED_VERSION_ti-xdctools	= "310"
PREFERRED_VERSION_ti-codec-combo-dm6446	= "205"

# tconf from xdctools dislikes '.' in pwd :/
PR = "r9"
PV = "2231"

# NOTE: This in internal ftp running on Brijesh's linux host.
# This will not work outside TI network and the link should be remove once
# we get external http:// URL
OE_ALLOW_INSECURE_DOWNLOADS = "1"
SRC_URI = "ftp://156.117.95.201/codec_engine_2_23_01.tar.gz "

# Set the source directory
S = "${WORKDIR}/codec_engine_2_23_01"

do_compile () {
    echo "! Do not rebuild for now !"
}

do_install () {
	# install codec engine source for dev pkg
	install -d ${D}/opt/ti/sdk/codec_engine_2_23_01/
    cp -pPrf ${S}/* ${D}/opt/ti/sdk/codec_engine_2_23_01/ 
}

# stage tree - other packages may need this
do_stage() {
    install -d ${STAGING_DIR}/${MULTIMACH_TARGET_SYS}/${PN}
    cp -pPrf ${S}/* ${STAGING_DIR}/${MULTIMACH_TARGET_SYS}/${PN}/ 
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN}-dev += "/opt/ti/sdk/codec_engine_2_23_01/*"
INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN}-dev = True
