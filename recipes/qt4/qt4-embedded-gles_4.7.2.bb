require recipes/qt4/qt4-embedded.inc
require ../arago/recipes/ti/ti-paths.inc
PR = "${INC_PR}.14"

PROVIDES += "qt4-embedded"
RPROVIDES_${PN} += "qt4-embedded"
RPROVIDES_${PN}-dev += "qt4-embedded-dev"

QT_GLFLAGS = "-opengl es2 -depths 16,24,32 -plugin-gfx-powervr -D QT_QWS_CLIENTBLIT"

require recipes/qt4/qt-${PV}.inc

SRC_URI += "file://cursor-hack.diff \
            file://0001-wsegl2-support.patch \
"
SRC_URI_append_c6a811x-evm = " \
            file://0001-bitblt-add-support-for-bitblt-screen-driver.patch \
"

FILESPATHBASE .= ":${OEBASE}/arago-oe-dev/recipes/qt4"
FILESPATHPKG .= ":qt4-embedded-${PV}:qt4-embedded"

DEPENDS += "virtual/egl"
require recipes/egl/egl.inc

QT_CONFIG_FLAGS += " \
 -exceptions \
"
QT_CONFIG_FLAGS_append_c6a811x-evm = " \
 -plugin-gfx-bitblt \
"
DEPENDS = "ti-linuxutils ti-bb2ddrv"
DEPENDS += "virtual/kernel"

#EXTRA_ENV_append_c6a811x-evm = '-I/datalocal/amit/ezsdk/arago-tmp/work/armv7a-none-linux-gnueabi/amitamit'
EXTRA_ENV_append_c6a811x-evm = '-I${LINUXUTILS_INSTALL_DIR}/packages/ti/sdo/linuxutils/cmem \
                                -I${LINUXUTILS_INSTALL_DIR}/packages/ti/sdo/linuxutils/cmem/include \
                                -I${LINUXUTILS_INSTALL_DIR}/packages/ti/sdo/linuxutils/cmem/lib \
                                -I${WORKDIR}/../../c6a811x-evm-none-linux-gnueabi/bb2ddrv-00.00.01-r0/linux_bltsville_bb2d_00.00.01 \
                                -I${WORKDIR}/../../c6a811x-evm-none-linux-gnueabi/bb2ddrv-00.00.01-r0/linux_bltsville_bb2d_00.00.01/include/bltsville \
			        -I${WORKDIR}/../../c6a811x-evm-none-linux-gnueabi/bb2ddrv-00.00.01-r0/linux_bltsville_bb2d_00.00.01/include/ocd'

PACKAGES += " \
${QT_BASE_NAME}-qmlimports-folderlistmodel \
${QT_BASE_NAME}-qmlimports-folderlistmodel-dbg \
${QT_BASE_NAME}-qmlimports-gestures \
${QT_BASE_NAME}-qmlimports-gestures-dbg \
${QT_BASE_NAME}-qmlimports-particles \
${QT_BASE_NAME}-qmlimports-particles-dbg \
${QT_BASE_NAME}-qmlimports-webkit \
${QT_BASE_NAME}-qmlimports-webkit-dbg \
"

FILES_${QT_BASE_NAME}-qmlimports-folderlistmodel-dbg = "/usr/imports/Qt/labs/folderlistmodel/.debug/*"
FILES_${QT_BASE_NAME}-qmlimports-folderlistmodel = "/usr/imports/Qt/labs/folderlistmodel/*"
FILES_${QT_BASE_NAME}-qmlimports-gestures-dbg = "/usr/imports/Qt/labs/gestures/.debug/*"
FILES_${QT_BASE_NAME}-qmlimports-gestures = "/usr/imports/Qt/labs/gestures/*"
FILES_${QT_BASE_NAME}-qmlimports-particles-dbg = "/usr/imports/Qt/labs/particles/.debug/*"
FILES_${QT_BASE_NAME}-qmlimports-particles = "/usr/imports/Qt/labs/particles/*"
FILES_${QT_BASE_NAME}-qmlimports-webkit-dbg = "/usr/imports/QtWebKit/.debug/*"
FILES_${QT_BASE_NAME}-qmlimports-webkit = "/usr/imports/QtWebKit/*"
