require ../ti-cgt6x.inc
inherit sdk

SRC_URI	= "http://install.source.dir.com/ti_cgt_c6000_6.1.9_setup_linux_x86.bin"

BINFILE="ti_cgt_c6000_6.1.9_setup_linux_x86.bin"

S = "${WORKDIR}/cgt"

# Yes, the xdc stuff still breaks with a '.' in PWD
PV = "6190"
PR = "r1"

do_install() {
	install -d ${D}/${prefix}/dvsdk/cg6x_6_1_9
    cp -pPrf ${S}/* ${D}/${prefix}/dvsdk/cg6x_6_1_9
	
	# Creates rules.make file
	mkdir -p ${STAGING_DIR_HOST}/ti-sdk-rules
	echo "# Where the TI C6x codegen tool is installed." >  ${STAGING_DIR_HOST}/ti-sdk-rules/cgt6x.Rules.make
	echo "CODEGEN_INSTALL_DIR=${prefix}/dvsdk/cg6x_6_1_9" >> ${STAGING_DIR_HOST}/ti-sdk-rules/cgt6x.Rules.make
  echo "" >> 	${STAGING_DIR_HOST}/ti-sdk-rules/cgt6x.Rules.make
}

INHIBIT_PACKAGE_STRIP = "1"
FILES_${PN} = "${prefix}/dvsdk/cg6x_6_1_9"
INSANE_SKIP_${PN} = True