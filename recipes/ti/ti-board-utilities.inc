DESCRIPTION = "Package contains board utilies for TI platforms"
LICENSE = "TI"

require ti-paths.inc
require ti-staging.inc

SRC_URI = "http://install.source.dir.local/board_utilities_${PV}.tar.gz;name=boardutilitiestarball"

S = "${WORKDIR}/board-utilities"

do_compile () {
	cd ${S}
}

do_install () {
	install -d ${D}/${installdir}/ti-psp-tree/board-utilities
        cp -pPrf ${S}/* ${D}/${installdir}/ti-psp-tree/board-utilities
}

FILES_${PN} = "${installdir}/*"
PACKAGE_STRIP = "no"
INSANE_SKIP_${PN} = True

