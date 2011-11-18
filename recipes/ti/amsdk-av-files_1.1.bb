DESCRIPTION = "AMSDK multimedia support files"
HOMEPAGE = "https://gforge.ti.com/gf/project/am_multimedia/"
LICENSE = "CC-BY-NC-ND-3.0"
SECTION = "multimedia"
PRIORITY = "optional"

PR = "r2"

COMPATIBLE_MACHINE = "(omap3evm|am37x-evm|am3517-evm|am389x-evm|beagleboard|am335x-evm)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

VIDEO_FILES = "video_480p "
VIDEO_FILES_append_am37x-evm = "video_vga_r"
VIDEO_FILES_append_beagleboard = "video_vga"
VIDEO_FILES_append_am3517-evm = "video_wqvga"
VIDEO_FILES_append_am335x-evm = "video_wvga"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/564/5179/amsdk-av-files_${PV}.tar.gz;name=avfilestarball \
           https://gforge.ti.com/gf/download/frsrelease/730/5217/armmultimedia_1.4.tar.gz;name=matrixfilestarball"

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

SRC_URI[avfilestarball.md5sum] = "19d254541f7c050d6220bd11249c1e8c"
SRC_URI[avfilestarball.sha256sum] = "5b81f49a305c3c88a6e33834d30561e8c16a534f9dcd10d31b348fd0031b2a74"

SRC_URI[matrixfilestarball.md5sum] = "f25d9ea7de0bbc327520f41a8ef41b0e"
SRC_URI[matrixfilestarball.sha256sum] = "99d28c9e2e2a4db3fd1db30f7de5fa1c09dd8150c6e9129dd8c672a453a5a69c"
