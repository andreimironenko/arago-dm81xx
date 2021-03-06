DESCRIPTION = "TI Inter Process Ccommunication (IPC) Mechanisms (for Uni- and Multi- Processor Configurations)"
HOMEPAGE = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/ipc/index.html"

SECTION = "devel"
LICENSE = "BSD" 

require ti-paths.inc
require ti-staging.inc
require ti-eula-unpack.inc

PR = "r4"

PVExtra = ""

S = "${WORKDIR}/ti/ipc_${PV}${PVExtra}"

SRC_URI = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/ipc/${PV}/exports/ipc_setuplinux_${PV}${PVExtra}.bin;name=ipcbin"

SRC_URI =+ "file://ipc.pc"

DEPENDS = "ti-sysbios ti-cgt6x ti-xdctools"

BINFILE="ipc_setuplinux_${PV}${PVExtra}.bin"
TI_BIN_UNPK_CMDS="Y: q:workdir:Y"

do_configure() {
    sed -i -e s:@VERSION@:${PV}:g ${WORKDIR}/ipc.pc
    
    cp ${WORKDIR}/ipc.pc ${S}
}

do_install() {
    install -d ${D}${IPC_INSTALL_DIR_RECIPE}
    cp -pPrf ${S}/* ${D}${IPC_INSTALL_DIR_RECIPE}
   
    install -d ${D}/${libdir}/pkgconfig
    install ${S}/ipc.pc ${D}/${libdir}/pkgconfig/
}

FILES_ti-ipc-dev = "${libdir}/*"
