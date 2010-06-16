DESCRIPTION = "Matrix GUI"
HOMEPAGE = "https://gforge.ti.com/gf/project/matrix_gui/"
LICENSE = "BSD"
SECTION = "multimedia"
PRIORITY = "optional"

SRCREV = "58"
PV = "1.0"
PR = "r9+svnr${SRCPV}"

PLATFORM_da850-omapl138-evm = "omapl138"
PLATFORM ?= "dm3730-am3715-evm"

# Skip the QA for browser binary (temporary)
INSANE_SKIP_${PN} = "True"

SRC_URI = "svn://gforge.ti.com/svn/matrix_gui/;module=trunk;proto=https;user=anonymous;pswd='' \
file://0001-matrix_gui-update-the-icon-font-spacing-to-look-goo.patch \
file://0002-matrix-gui-move-the-data-files-html-bin-in-platfo.patch \
file://0003-matrix-gui-add-script-to-launch-qtdemo-browser-from.patch \
"

S = "${WORKDIR}/trunk"

INITSCRIPT_NAME = "matrix-gui"
INITSCRIPT_PARAMS = "defaults 99"

CXXFLAGS_da850-omapl138-evm_append = " -DPlatform_omapl138 "
PACKAGE_ARCH = ${MACHINE_ARCH}

inherit qt4e update-rc.d

do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/matrix_gui ${D}/${bindir}
	cp -ar  ${S}/${PLATFORM}/bin/* ${D}/${bindir}/
	chmod 755  ${D}/${bindir}/*
	install -d ${D}/${datadir}/matrix/html
	cp -ar ${S}/${PLATFORM}/html/* ${D}/${datadir}/matrix/html
	install -d ${D}/${datadir}/matrix/images
	install -m 0644 ${S}/images/*.png ${D}/${datadir}/matrix/images/
	install -d ${D}${sysconfdir}/init.d/
	install -c -m 0755 ${S}/${PLATFORM}/etc/init ${D}${sysconfdir}/init.d/matrix-gui
}

RRECOMMENDS_${PN} = "qt4-embedded-plugin-mousedriver-tslib"
FILES_${PN} += "${datadir}/matrix/*"