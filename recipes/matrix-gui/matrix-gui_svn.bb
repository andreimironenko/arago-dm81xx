DESCRIPTION = "Matrix GUI"
HOMEPAGE = "https://gforge.ti.com/gf/project/matrix_gui/"
LICENSE = "BSD"
SECTION = "multimedia"
PRIORITY = "optional"

SRCREV = "155"
PV = "1.0"
PR = "r14+svnr${SRCPV}"

PLATFORM_dm365 = "dm365"
PLATFORM_da850-omapl138-evm = "omapl138"
PLATFORM_omap3evm = "omap3530"
PLATFORM_dm37x-evm = "dm3730"
PLATFORM ?= "<UNDEFINED>"

# Skip the QA for browser binary (temporary)
INSANE_SKIP_${PN} = "True"

SRC_URI = "svn://gforge.ti.com/svn/matrix_gui/;module=branches/BRANCH_BRIJESH;proto=https;user=anonymous;pswd='' \
"

S = "${WORKDIR}/branches/BRANCH_BRIJESH"

do_prepsources() {
    # remove .svn files.
    find ${S} -name .svn -type d | xargs rm -rf
}

# run prepare sources before do_patch to ensure that sourceipk picks the right sources.
addtask prepsources after do_unpack before do_patch

INITSCRIPT_NAME = "matrix-gui"
INITSCRIPT_PARAMS = "defaults 99"

CXXFLAGS_da850-omapl138-evm_append = " -DPlatform_omapl138 "
CXXFLAGS_dm365_append = " -DPlatform_dm365 "
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
