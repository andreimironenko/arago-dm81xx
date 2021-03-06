# Set some preferences
PREFERRED_PROVIDER_initscripts = "initscripts"
PREFERRED_PROVIDER_ncurses-tools = "ncurses"
PREFERRED_PROVIDER_libopkg-dev = "opkg"
PREFERRED_PROVIDER_ti-lpm-module = "ti-local-power-manager"
PREFERRED_PROVIDER_ti-dsplink-module = "ti-dsplink"
PREFERRED_PROVIDER_qt4-embedded = "${ARAGO_QT_PROVIDER}"

# Use the git version of tslib which works with latest 2.6.37 kernels
PREFERRED_VERSION_tslib = "1.0+gitr%"

# Version 0.7.3_0.8.0-rc of wpa-supplicant and hostap-daemon is required 
# for AM-SDK and the mac80211 wireless stack
PREFERRED_VERSION_wpa-supplicant = "0.7.3+0.8.0-rc"
PREFERRED_VERSION_hostap-daemon = "0.7.3+0.8.0-rc"
PREFERRED_VERSION_wpa-gui-e = "0.7.3+0.8.0-rc"

# Lock udev at 141, as recent versions cannot be built with csl2009q1
PREFERRED_VERSION_udev = "141"

PREFERRED_VERSION_gstreamer = "0.10.32"
PREFERRED_VERSION_gst-plugins-base = "0.10.32"
PREFERRED_VERSION_gst-plugins-good = "0.10.28"
PREFERRED_VERSION_gst-plugins-bad = "0.10.21"
PREFERRED_VERSION_gst-plugins-ugly = "0.10.16"

# Lock down older libxml2-native for RHEL4 compatibility
PREFERRED_VERSION_libxml2-native = "2.7.3"

# Override gcc-4.3 defaults, when building gcc-4.5 toolchain
ANGSTROM_GCC_VERSION			= "4.5"
ANGSTROM_BINUTILS_VERSION		= "2.20.1"
ANGSTROM_BINUTILS_VERSION_armv7a	= "2.20.1"
PREFERRED_VERSION_eglibc		= "2.12"
PREFERRED_VERSION_eglibc-initial	= "2.12"

# Select newer fakeroot, as others are unfetchable
PREFERRED_VERSION_fakeroot = "1.14.5"
PREFERRED_VERSION_fakeroot-native = "1.14.5"

# Pin down util-linux-ng at 2.17 for GPLv3-less mods
PREFERRED_VERSION_util-linux-ng = "2.17"
