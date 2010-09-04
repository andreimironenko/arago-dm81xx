require ti-dvsdk-demos.inc

COMPATIBLE_MACHINE = "(dm365-evm)"

PV = "4_00_00_03"
SRCREV         = "9631"
DEMOSBRANCH    = "tags/TAG_${PV}"

SRC_URI = "svn://winsvn.sanb.design.ti.com/SDOApps/apps/dvsdk_demos/;module=${DEMOSBRANCH};proto=http;user=anonymous;pswd='' \
	file://doxygen_templates.tar.gz \
	file://arago-tdox "

PR = "${INC_PR}.1"
S  = "${WORKDIR}/${DEMOSBRANCH}/dvsdk_demos/demos"
RDEPENDS_append = " ti-dvsdk-demos-qtinterface "

do_install_prepend () {
    find ${S} -name .svn -type d | xargs rm -rf
    cp -pPrf ${WORKDIR}/doxygen_templates ${S}
    cp -pPrf ${WORKDIR}/arago-tdox ${S}/tdox
    chmod a+x ${WORKDIR}/${DEMOSBRANCH}/dvsdk_demos/release.sh
    chmod a+x ${WORKDIR}/${DEMOSBRANCH}/dvsdk_demos/demos/tdox
    ${WORKDIR}/${DEMOSBRANCH}/dvsdk_demos/release.sh ${PV}

    rm -rf ${S}/doxygen_templates
    rm -rf ${S}/tdox
    rm -rf ${S}/dvsdk_demos_${PV}
    rm -rf ${S}/patches
}

