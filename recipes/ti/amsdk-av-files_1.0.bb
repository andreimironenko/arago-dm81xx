DESCRIPTION = "Audio/Video files for use with the AMSDK"
HOMEPAGE = "https://gforge.ti.com/gf/project/am_multimedia/"
LICENSE = "CC-BY-NC-ND-3.0"
SECTION = "multimedia"
PRIORITY = "optional"

PR = "r6"

COMPATIBLE_MACHINE = "(omap3evm|am37x-evm|am3517-evm|am389x-evm|beagleboard)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

VIDEO_FILES = "video_480p "
VIDEO_FILES_append_am37x-evm = "video_vga_r"
VIDEO_FILES_append_beagleboard = "video_vga"
VIDEO_FILES_append_am3517-evm = "video_wqvga"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/564/4811/amsdk-av-files-${PV}.tar.gz;name=avfilestarball \
           https://gforge.ti.com/gf/download/frsrelease/669/5092/armmultimedia_1.1.tar.gz;name=matrixfilestarball"

S = "${WORKDIR}/amsdk-av-files"

MATRIX_FILES_DIR = "${WORKDIR}/armmultimedia"
require recipes/matrix/matrix-gui-apps.inc

do_compile() {
    :
}

do_install() {
    install -d ${D}${datadir}/ti \
       ${D}${datadir}/ti/audio \
       ${D}${datadir}/ti/video
    install -m 0644 ${S}/audio/* ${D}${datadir}/ti/audio/
    for file in ${VIDEO_FILES}; do
	install -m 0644 ${S}/${file}/* ${D}${datadir}/ti/video/
    done
}

# Make sure the multimedia submenu has been installed
RDEPENDS += matrix-gui-submenus-multimedia

# Add the matrix directory to the list of FILES
FILES_${PN} += "${MATRIX_BASE_DIR}/*"

FILES_${PN} += "${datadir}/ti/*"

SRC_URI[avfilestarball.md5sum] = "06f3bffe82305daf01574f046a3735a0"
SRC_URI[avfilestarball.sha256sum] = "313637419f52b7e54b328dc309f5d6309ce37d5015ba20d931f41883cb3447f9"

SRC_URI[matrixfilestarball.md5sum] = "56454246cd6ea0693446c63e4b136962"
SRC_URI[matrixfilestarball.sha256sum] = "f9c31160582d2907e9a7bdf7a081fd7b8f90d8136682d85145ed33a6c1e5ea40"
