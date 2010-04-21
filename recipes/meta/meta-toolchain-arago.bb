PR = "r7"

SDK_DISTRO = "arago"
TOOLCHAIN_OUTPUTNAME = "${SDK_DISTRO}-${DISTRO_VERSION}-${FEED_ARCH}-${TARGET_OS}-${SDK_SUFFIX}"

TOOLCHAIN_HOST_TASK ?= "task-arago-toolchain-host"
TOOLCHAIN_TARGET_TASK ?= "task-arago-toolchain-target"

# External CSL toolchain/glibc
TOOLCHAIN_TARGET_EXCLUDE += "\
    libc6 \
    libc6-dev \
    glibc-extra-nss \
    libgcc1 \
    linux-libc-headers-dev \
    libthread-db1 \
    sln \
    "

# X11 dependencies
TOOLCHAIN_TARGET_EXCLUDE += "\
    bigreqsproto \
    bigreqsproto-dev \
    inputproto \
    inputproto-dev \
    kbproto \
    kbproto-dev \
    libice6 \
    libice-dev \
    libsm6 \
    libsm-dev \
    libx11-6 \
    libx11-dev \
    libxau6 \
    libxau-dev \
    libxdmcp6 \
    libxdmcp-dev \
    xcmiscproto \
    xcmiscproto-dev \
    xextproto \
    xextproto-dev \
    xf86bigfontproto \
    xf86bigfontproto-dev \
    xproto \
    xproto-dev \
    xtrans-dev \
    util-macros \
    util-macros-dev \
    "

# GStreamer/media dependencies
TOOLCHAIN_TARGET_EXCLUDE += "\
    gst-plugins-base \
    gst-plugins-base-dev \
    gstreamer \
    gstreamer-dev \
    libgstapp-0.10-0 \
    libgstaudio-0.10-0 \
    libgstcdda-0.10-0 \
    libgstfft-0.10-0 \
    libgstinterfaces-0.10-0 \
    libgstnetbuffer-0.10-0 \
    libgstpbutils-0.10-0 \
    libgstriff-0.10-0 \
    libgstrtp-0.10-0 \
    libgstrtsp-0.10-0 \
    libgstsdp-0.10-0 \
    libgsttag-0.10-0 \
    libgstvideo-0.10-0 \
    libid3tag0 \
    libid3tag-dev \
    libmad0 \
    libmad-dev \
    libmikmod \
    libmikmod-dev \
    libogg0 \
    libogg-dev \
    libvorbis0 \
    libvorbis-dev \
    libvorbisidec1 \
    libvorbisidec-dev \
    libsndfile1 \
    libsndfile-dev \
    "

# OpenSSL dependencies
#TOOLCHAIN_TARGET_EXCLUDE += "\
#    libcrypto0.9.8 \
#    libssl0.9.8 \
#    openssl \
#    openssl-dev \
#    "

# GnuTLS/libgcrypt dependencies
TOOLCHAIN_TARGET_EXCLUDE += "\
    libgcrypt11 \
    libgcrypt-dev \
    libgnutls26 \
    libgnutls-dev \
    libgnutls-extra26 \
    libgnutls-openssl26 \
    libgnutlsxx26 \
    libgpg-error0 \
    libgpg-error-dev \
    "

# PostgreSQL dependencies
TOOLCHAIN_TARGET_EXCLUDE += "\
    libpq4 \
    libpq-dev \
    postgresql \
    postgresql-dev \
    "

TOOLCHAIN_TARGET_EXCLUDE += "\
    curl \
    opkg-nogpg-nocurl \
    alsa-utils \
    alsa-conf-base \
    update-rc.d \
    update-rc.d-dev \
    tslib-conf \
    pointercal \
    sysvinit \
    sysvinit-inittab \
    i2c-tools \
    mtd-utils \
    util-linux-ng-fdisk \
    util-linux-ng-cfdisk \
    util-linux-ng-sfdisk \
    util-linux-ng-mount \
    util-linux-ng-mountall \
    util-linux-ng-umount \
    util-linux-ng-losetup \
    util-linux-ng-swaponoff \
    util-linux-ng-readprofile \
    util-linux-ng-blkid \
    util-linux-ng-fsck \
    util-linux-ng \
    udev-utils \
    dbus \
    wpa-supplicant-passphrase \
    "

require meta-toolchain.bb
SDK_SUFFIX = "sdk"
