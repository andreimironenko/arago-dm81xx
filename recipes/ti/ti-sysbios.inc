DESCRIPTION = "TI SYS/BIOS v6 Kernel"
HOMEPAGE = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/bios/sysbios"
SECTION = "devel"
LICENSE = "BSD"

require ti-paths.inc
require ti-staging.inc
require ti-eula-unpack.inc

PR = "r5"
PVExtra = ""

S = "${WORKDIR}/ti/bios_${PV}${PVExtra}"

SRC_URI = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/bios/sysbios/${PV}${PVExtra}/exports/bios_setuplinux_${PV}${PVExtra}.bin;name=sysbiosbin"

BINFILE="bios_setuplinux_${PV}${PVExtra}.bin"
TI_BIN_UNPK_CMDS="Y: q:workdir:Y"

do_install() {
    install -d ${D}${SYSBIOS_INSTALL_DIR_RECIPE}
    cp -pPrf ${S}/* ${D}${SYSBIOS_INSTALL_DIR_RECIPE}
}

