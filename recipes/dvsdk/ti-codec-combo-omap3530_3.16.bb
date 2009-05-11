DESCRIPTION = "OMAP3530 Codec Combo 3.16"

# NOTE: This in internal ftp running on Brijesh's linux host.
# This will not work outside TI network and the link should be remove once
# we get external http:// URL
OE_ALLOW_INSECURE_DOWNLOADS = "1"
SRC_URI	= "ftp://156.117.95.201/omap3530_dvsdk_combos_3_16.tar.gz"

S = "${WORKDIR}/omap3530_dvsdk_combos_3_16"

# Yes, the xdc stuff still breaks with a '.' in PWD
PV = "316"
PR = "r6"

do_compile() {
  	echo "Do not rebuild for now"
}

do_install () {
    install -d ${D}/opt/ti/codec-combo
	cd ${S}
	for file in `find . -name *.x64P`; do
		cp ${file} ${D}/opt/ti/codec-combo
	done

	# install codec combo on dev pkg
	install -d ${D}//opt/ti/sdk/omap3530_codec_combo_3_16/
	cp -pRrf ${S}/* ${D}/opt/ti/sdk/omap3530_codec_combo_3_16/
}

do_stage() {
    install -d ${STAGING_DIR}/${MULTIMACH_TARGET_SYS}/${PN}
    cp -pPrf ${S}/* ${STAGING_DIR}/${MULTIMACH_TARGET_SYS}/${PN}/ 
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN} = "/opt/ti/codec-combo/*"
INSANE_SKIP_${PN}-dev = True
FILES_${PN}-dev = "/opt/ti/sdk/omap3530_codec_combo_3_16/*"
