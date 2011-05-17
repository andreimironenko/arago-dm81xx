DESCRIPTION = "Initialization and demo application for bluetooth on WL1271 chipset"
SECTION = "network"
LICENSE = "BSD"
DEPENDS += "virtual/kernel openobex"
RDEPENDS += "bluez4 openobex ussp-push obexftp bluez-hcidump"

PR = "r0"

PACKAGE_ARCH = ${MACHINE_ARCH}

MACHINE_KERNEL_PR_append = "a"

COMPATIBLE_MACHINE = "am37x-evm|am180x-evm|da850-omapl138-evm"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/556/4630/wl1271-bluetooth-2011-05-17.tar"
S = "${WORKDIR}/wl1271-bluetooth"

inherit module

PLATFORM_am37x-evm = "omap3evm"
PLATFORM_am180x-evm = "am1808"
PLATFORM_da850-omapl138-evm = "am1808"
PLATFORM ?= "UNKNOWN"

EXTRA_OEMAKE = " \
	KERNEL_DIR=${STAGING_KERNEL_DIR} \
"

do_compile() {
	# Build opp server application
	cd ${S}/oppserver && ${CC} -o oppserver *.c ${LDFLAGS} ${CFLAGS} -lopenobex -lbluetooth -lmisc

	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS

	# Build bluetooth enable kernel module
	cd ${S}/BT_enable 
	cp gpio_en_${PLATFORM}.c gpio_en.c 
	oe_runmake 
}

do_install () {
	install -d ${D}${bindir}
	install -d ${D}${base_libdir}/firmware
	install -d ${D}${datadir}/wl1271-demos
	install -d ${D}${datadir}/wl1271-demos/bluetooth
	install -d ${D}${datadir}/wl1271-demos/bluetooth/gallery
	install -d ${D}${datadir}/wl1271-demos/bluetooth/scripts
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bt_enable

	install -m 0755 ${S}/oppserver/oppserver ${D}${bindir}
	install -m 0755 ${S}/BT_firmware/${PLATFORM}/* ${D}${base_libdir}/firmware
	install -m 0755 ${S}/gallery/* ${D}${datadir}/wl1271-demos/bluetooth/gallery
	install -m 0755 ${S}/script/common/* ${D}${datadir}/wl1271-demos/bluetooth/scripts
	install -m 0755 ${S}/script/${PLATFORM}/* ${D}${datadir}/wl1271-demos/bluetooth/scripts
	install -m 0755 ${S}/BT_enable/gpio_en.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bt_enable

}

FILES_${PN} +=" \
	${bindir} \
	${base_libdir}/firmware \
	${datadir}/wl1271-demos/bluetooth/scripts \
	${datadir}/wl1271-demos/bluetooth/gallery \
	"

FILES_${PN}-dbg += "${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bt_enable/.debug/*"

PACKAGE_STRIP = "no"

SRC_URI[md5sum] = "6c3a11a1d1676062c7fd5ba2b764122f"
SRC_URI[sha256sum] = "e7463d9bbf21f2efdfe36c1819580b6ff56879c5ee241f61115b3c085d2cb1c3"
