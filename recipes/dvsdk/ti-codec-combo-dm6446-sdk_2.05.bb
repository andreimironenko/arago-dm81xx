DESCRIPTION = "DM6446 Codec Combo 2.05"
inherit sdk

# NOTE: This in internal ftp running on Brijesh's linux host.
# This will not work outside TI network and the link should be remove once
# we get external http:// URL
OE_ALLOW_INSECURE_DOWNLOADS = "1"
SRC_URI	= "ftp://156.117.95.201/dm6446_dvsdk_combos_2_05.tar.gz"

S = "${WORKDIR}/dm6446_dvsdk_combos_2_05"

# Yes, the xdc stuff still breaks with a '.' in PWD
PV = "205"
PR = "r8"

# DM6446 combo has Makefile. We don't want to rebuild anything here.
do_compile() {
	echo "do nothing"
}

do_install() {
    install -d ${D}/${prefix}/dvsdk/dm6446_dvsdk_combos_2_05
    cp -pPrf ${S}/* ${D}/${prefix}/dvsdk/dm6446_dvsdk_combos_2_05

    # Creates rules.make file
	  mkdir -p ${STAGING_DIR_HOST}/ti-sdk-rules
	  echo "# Where the codec servers are installed." > ${STAGING_DIR_HOST}/ti-sdk-rules/codec.Rules.make
    echo "CODEC_INSTALL_DIR=${prefix}/dvsdk/dm6446_dvsdk_combos_2_05" >> ${STAGING_DIR_HOST}/ti-sdk-rules/codec.Rules.make
}

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} = True
FILES_${PN} = "${prefix}/dvsdk/dm6446_dvsdk_combos_2_05/*"
