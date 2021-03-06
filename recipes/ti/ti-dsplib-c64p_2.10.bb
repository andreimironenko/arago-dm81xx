DESCRIPTION = "TI DSPLIB Optimised DSP Library of C64x+ devices"
HOMEPAGE = "http://focus.ti.com/docs/toolsw/folders/print/sprc265.html"
SECTION = "multimedia"
LICENSE = "TI"

# TODO : Add variants for 67x/674x/etc
# TODO : Add compile step to enable rebuild

PV = "2_10"

SRC_URI[dsplibgz.md5sum] = "0e9f0fc05b17d8eefc6117f36ecd576e"
SRC_URI[dsplibgz.sha256sum] = "96e320a3dcb8b238e5245a36b26c2f7f1d00a8467ab65d6c5c93f5f57c891252"

PR = "r4"

require ti-paths.inc
require ti-staging.inc
require ti-eula-unpack.inc

S = "${WORKDIR}/C64x+DSPLIB/dsplib_v210"

SRC_URI = "http://focus.ti.com/lit/sw/sprc834/sprc834.gz;name=dsplibgz"

#Later this will have dependencies when we rebuild the libraries/examples
#DEPENDS = "ti-cgt6x ti-xdctools ti-dspbios ti-codec-engine" 

PRETARFILE="sprc834"
BINFILE="C64x+DSPLIB-2.1-Linux-Install.bin"
TI_BIN_UNPK_CMDS="Y:workdir:"

python do_unpack () {
    bb.build.exec_func('base_do_unpack', d)
    bb.build.exec_func('ti_pretar_do_unpack', d)
    bb.build.exec_func('ti_bin_do_unpack', d)
}

do_install() {

    install -d ${D}${DSPLIB_C64P_INSTALL_DIR_RECIPE}
    cp -pPrf ${S}/* ${D}${DSPLIB_C64P_INSTALL_DIR_RECIPE}
}

