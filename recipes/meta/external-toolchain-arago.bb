require external-toolchain-arago.inc

PR = "r10"

INHIBIT_DEFAULT_DEPS = "1"

PACKAGE_NO_GCONV = "1"
PACKAGE_NO_LOCALE = "1"

ALLOW_EMPTY_libgcc = "1"
ALLOW_EMPTY_libstdc++ = "1"

INSANE_SKIP_libgcc = "True"
INSANE_SKIP_libstdc++ = "True"
INSANE_SKIP_glibc-utils = "True"
INSANE_SKIP_glibc-dev = "True"
INSANE_SKIP_gdbserver = "True"

SRC_URI = "file://SUPPORTED"

PROVIDES = "\
	virtual/${TARGET_PREFIX}gcc \
	virtual/${TARGET_PREFIX}g++ \
	virtual/${TARGET_PREFIX}gcc-initial \
	virtual/${TARGET_PREFIX}gcc-intermediate \
	virtual/${TARGET_PREFIX}binutils \
	virtual/${TARGET_PREFIX}libc-for-gcc \
	virtual/${TARGET_PREFIX}libc-initial \
	virtual/libc \
	virtual/libintl \
	virtual/libiconv \
	glibc-thread-db \
	${@base_conditional('PREFERRED_PROVIDER_linux-libc-headers', 'external-toolchain-arago', 'linux-libc-headers linux-libc-headers-dev', '', d)} \
	${@base_conditional('PREFERRED_PROVIDER_gdbserver', 'external-toolchain-arago', 'gdbserver', '', d)} \
	${@base_conditional('PREFERRED_PROVIDER_binutils-dev', 'external-toolchain-arago', 'binutils-dev', '', d)} \
"

DEPENDS = "${@base_conditional('PREFERRED_PROVIDER_linux-libc-headers', 'external-toolchain-arago', '', 'linux-libc-headers', d)}"
RPROVIDES_glibc-dev += "libc-dev libc6-dev virtual-libc-dev"
PACKAGES_DYNAMIC += "glibc-gconv-*"
PACKAGES_DYNAMIC += "glibc-locale-*"

LEAD_SONAME = "libc.so.6"

PACKAGES = "\
	libgcc \
	libgcc-dev \
	libstdc++ \
	libstdc++-dev \
	${@base_conditional('PREFERRED_PROVIDER_linux-libc-headers', 'external-toolchain-arago', 'linux-libc-headers-dev', '', d)} \
	${@base_conditional('PREFERRED_PROVIDER_gdbserver', 'external-toolchain-arago', 'gdbserver', '', d)} \
	${@base_conditional('PREFERRED_PROVIDER_binutils-dev', 'external-toolchain-arago', 'binutils-dev', '', d)} \
	glibc-dbg \
	glibc \
	catchsegv \
	sln \
	nscd \
	ldd \
	localedef \
	glibc-utils \
	glibc-dev \
	glibc-locale \
	libsegfault \
	glibc-extra-nss \
	glibc-thread-db \
	glibc-pcprofile \
"

FILES_glibc = "\
	${sysconfdir} \
	${libexecdir}/* \
	${datadir}/zoneinfo \
	/lib/libc* \
	/lib/libm* \
	/lib/ld* \
	/lib/libpthread* \
	/lib/libresolv* \
	/lib/librt* \
	/lib/libutil* \
	/lib/libnsl* \
	/lib/libnss_files* \
	/lib/libnss_compat* \
	/lib/libnss_dns* \
	/lib/libdl* \
	/lib/libanl* \
	/lib/libBrokenLocale* \
	/sbin/ldconfig \
"

FILES_glibc-dev = "\
	${includedir} \
	${libdir}/*.o \
	${bindir}/rpcgen \
	${libdir}/*.so \
	${libdir}/*.a \
"

FILES_glibc-dbg += " ${libdir}/gconv/.debug ${libexecdir}/*/.debug ${base_libdir}/.debug ${libdir}/.debug"
FILES_glibc-utils = "${bindir}/* ${sbindir}/*"
FILES_glibc-extra-nss = "/lib/libnss*"
FILES_glibc-gconv = "${libdir}/gconv/*"
FILES_glibc-pcprofile = "/lib/libpcprofile.so"
FILES_glibc-thread-db = "/lib/libthread_db*"

FILES_libgcc = "${base_libdir}/libgcc_s.so.1"
FILES_libgcc-dev = "${base_libdir}/libgcc_s.so"

FILES_libstdc++ = "${libdir}/libstdc++.so.*"
FILES_libstdc++-dev = "\
	${includedir}/c++/* \
	${libdir}/libstdc++.so \
	${libdir}/libstdc++.la \
	${libdir}/libstdc++.a \
	${libdir}/libsupc++.la \
	${libdir}/libsupc++.a \
"

FILES_binutils-dev = "\
	${includedir}/ansidecl.h \
	${includedir}/dis-asm.h \
	${includedir}/bfdlink.h \
	${includedir}/libiberty.h \
	${includedir}/symcat.h \
	${includedir}/bfd.h \
	${libdir}/libbfd* \
	${libdir}/libopcodes* \
	${libdir}/libiberty* \
"

FILES_linux-libc-headers-dev = "\
	${includedir}/asm* \
	${includedir}/drm \
	${includedir}/linux \
	${includedir}/mtd \
	${includedir}/rdma \
	${includedir}/scsi \
	${includedir}/sound \
	${includedir}/video \
"

FILES_libsegfault = "/lib/libSegFault*"
FILES_catchsegv = "${bindir}/catchsegv"
RDEPENDS_catchsegv = "libsegfault"

FILES_ldd = "${bindir}/ldd"
FILES_nscd = "${sbindir}/nscd*"
FILES_sln = "${base_sbindir}/sln"
FILES_localedef = "${bindir}/localedef"
FILES_gdbserver = "${bindir}/gdbserver"

DESCRIPTION_glibc-utils = "glibc: misc utilities like iconf, local, gencat, tzselect, rpcinfo, ..."
DESCRIPTION_glibc-extra-nss = "glibc: nis, nisplus and hesiod search services"
DESCRIPTION_ldd = "glibc: print shared library dependencies"
DESCRIPTION_nscd = "glibc: name service cache daemon for passwd, group, and hosts"
DESCRIPTION_sln = "glibc: create symbolic links between files"
DESCRIPTION_localedef = "glibc: compile locale definition files"
DESCRIPTION_gdbserver = "gdb: GNU debugger"
DESCRIPTION_binutils-dev = "binutils: GNU collection of binary utilities"

LICENSE = "${ARG_LIC_LIBC}"
LICENSE_ldd = "${ARG_LIC_LIBC}"
LICENSE_glibc = "${ARG_LIC_LIBC}"
LICENSE_glibc-thread-db = "${ARG_LIC_LIBC}"
LICENSE_libgcc = "${ARG_LIC_RLE}"
LICENSE_libgcc-dev = "${ARG_LIC_RLE}"
LICENSE_libstdc++ = "${ARG_LIC_RLE}"
LICENSE_libstdc++-dev = "${ARG_LIC_RLE}"
LICENSE_gdbserver = "${ARG_LIC_GDB}"
LICENSE_binutils-dev = "${ARG_LIC_BFD}"

PKGV = "${ARG_VER_MAIN}"
PKGV_libgcc = "${ARG_VER_GCC}"
PKGV_libgcc-dev = "${ARG_VER_GCC}"
PKGV_libstdc++ = "${ARG_VER_GCC}"
PKGV_libstdc++-dev = "${ARG_VER_GCC}"
PKGV_libc = "${ARG_VER_LIBC}"
PKGV_glibc = "${ARG_VER_LIBC}"
PKGV_glibc-dev = "${ARG_VER_LIBC}"
PKGV_glibc-dbg = "${ARG_VER_LIBC}"
PKGV_glibc-utils = "${ARG_VER_LIBC}"
PKGV_glibc-gconv = "${ARG_VER_LIBC}"
PKGV_glibc-extra-nss = "${ARG_VER_LIBC}"
PKGV_glibc-thread-db = "${ARG_VER_LIBC}"
PKGV_glibc-pcprofile = "${ARG_VER_LIBC}"
PKGV_catchsegv = "${ARG_VER_LIBC}"
PKGV_sln = "${ARG_VER_LIBC}"
PKGV_nscd = "${ARG_VER_LIBC}"
PKGV_ldd = "${ARG_VER_LIBC}"
PKGV_localedef = "${ARG_VER_LIBC}"
PKGV_libsegfault = "${ARG_VER_LIBC}"
PKGV_linux-libc-headers-dev = "${ARG_VER_KERNEL}"
PKGV_gdbserver = "${ARG_VER_GDB}"
PKGV_binutils-dev = "${ARG_VER_BFD}"

do_int_binutils() {
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${libdir}/{libbfd*,libopcodes*,libiberty*} ${D}${libdir}
}

do_ext_binutils() {
	rm -rf ${D}${includedir}/{ansidecl.h,dis-asm.h,bfdlink.h,libiberty.h,symcat.h,bfd.h}
}

do_install() {
	install -d ${D}${sysconfdir}
	install -d ${D}${bindir}
	install -d ${D}${sbindir}
	install -d ${D}${base_bindir}
	install -d ${D}${libdir}
	install -d ${D}${base_libdir}
	install -d ${D}${base_sbindir}
	install -d ${D}${datadir}
	install -d ${D}${includedir}

	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${base_libdir}/{lib*,ld*} ${D}${base_libdir}
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${base_sbindir}/ldconfig ${D}${base_sbindir}
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${bindir}/{gdbserver,gencat,getconf,getent,iconv,locale,mtrace,pcprofiledump,rpcgen,sprof,tzselect,xtrace} ${D}${bindir}
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${sbindir}/{iconvconfig,rpcinfo,zdump,zic} ${D}${sbindir}
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${includedir}/{arpa,asm*,bits,drm,gnu,linux,mtd,net*,nfs,protocols,rdma,rpc*,scsi,sound,sys*,video,*.h} ${D}${includedir}
	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}${libdir}/{?crt1.o,crt?.o,libBrokenLocale*,libanl*,libc.*,libc_*,libcrypt.*,libcidn.*,libdl.*,libg.*,libieee.*,libm.*,libmcheck.*,libnsl*,libnss*,libpthread*,libresolv*,librt*,libstdc*,libsupc*,libthread*,libutil*} ${D}${libdir}
	rm -rf ${D}${base_libdir}/*.la
	rm -rf ${D}${libdir}/*.la
	rm -rf ${D}${includedir}/{zconf,zlib}.h

	${@base_conditional('PREFERRED_PROVIDER_linux-libc-headers', 'external-toolchain-arago', '', 'rm -rf ${D}${includedir}/linux', d)}

	cp -a ${TOOLCHAIN_PATH}/${TARGET_SYS}/include/* ${D}${includedir}
	${@base_conditional('PREFERRED_PROVIDER_gdbserver', 'external-toolchain-arago', '', 'rm -rf ${D}${bindir}/gdbserver', d)}

	${@base_conditional('PREFERRED_PROVIDER_binutils-dev', 'external-toolchain-arago', 'do_int_binutils', 'do_ext_binutils', d)}

	sed -e "s# /lib# ../../lib#g" -e "s# /usr/lib# .#g" ${D}${libdir}/libc.so > ${D}${libdir}/temp
	mv ${D}${libdir}/temp ${D}${libdir}/libc.so

	sed -e "s# /lib# ../../lib#" -e "s# /usr/lib# .#g" ${D}${libdir}/libpthread.so > ${D}${libdir}/temp
	mv ${D}${libdir}/temp ${D}${libdir}/libpthread.so
}

TMP_LOCALE="/tmp/locale${libdir}/locale"

locale_base_postinst() {
#!/bin/sh

if [ "x$D" != "x" ]; then
  exit 1
fi

rm -rf ${TMP_LOCALE}
mkdir -p ${TMP_LOCALE}
if [ -f ${libdir}/locale/locale-archive ]; then
        cp ${libdir}/locale/locale-archive ${TMP_LOCALE}/
fi
localedef --inputfile=${datadir}/i18n/locales/%s --charmap=%s --prefix=/tmp/locale %s
mkdir -p ${libdir}/locale/
mv ${TMP_LOCALE}/locale-archive ${libdir}/locale/
rm -rf ${TMP_LOCALE}
}

locale_base_postrm() {
#!/bin/sh

rm -rf ${TMP_LOCALE}
mkdir -p ${TMP_LOCALE}
if [ -f ${libdir}/locale/locale-archive ]; then
	cp ${libdir}/locale/locale-archive ${TMP_LOCALE}/
fi
localedef --delete-from-archive --inputfile=${datadir}/locales/%s --charmap=%s --prefix=/tmp/locale %s
mv ${TMP_LOCALE}/locale-archive ${libdir}/locale/
rm -rf ${TMP_LOCALE}
}

python package_do_split_gconvs () {
	import os, re
	if (bb.data.getVar('PACKAGE_NO_GCONV', d, 1) == '1'):
		bb.note("package requested not splitting gconvs")
		return

	if not bb.data.getVar('PACKAGES', d, 1):
		return

	libdir = bb.data.getVar('libdir', d, 1)
	if not libdir:
		bb.error("libdir not defined")
		return
	datadir = bb.data.getVar('datadir', d, 1)
	if not datadir:
		bb.error("datadir not defined")
		return

	libcver = bb.data.getVar('ARG_VER_LIBC', d, 1)
	if not libcver:
		bb.error("ARG_VER_LIBC not defined")
		return

	gconv_libdir = os.path.join(libdir, "gconv")
	charmap_dir = os.path.join(datadir, "i18n", "charmaps")
	locales_dir = os.path.join(datadir, "i18n", "locales")
	binary_locales_dir = os.path.join(libdir, "locale")

	def set_pkg_ver(fn, pkg, file_regex, output_pattern, group):
		bb.data.setVar('PKGV_' + pkg, libcver, d)

	do_split_packages(d, gconv_libdir, file_regex='^(.*)\.so$', output_pattern='glibc-gconv-%s', description='gconv module for character set %s', hook=set_pkg_ver, extra_depends='glibc-gconv')

	do_split_packages(d, charmap_dir, file_regex='^(.*)\.gz$', output_pattern='glibc-charmap-%s', description='character map for %s encoding', hook=set_pkg_ver, extra_depends='')

	def calc_locale_deps(fn, pkg, file_regex, output_pattern, group):
		deps = []
		f = open(fn, "r")
		c_re = re.compile('^copy "(.*)"')
		i_re = re.compile('^include "(\w+)".*')
		for l in f.readlines():
			m = c_re.match(l) or i_re.match(l)
			if m:
				dp = legitimize_package_name('glibc-localedata-%s' % m.group(1))
				if not dp in deps:
					deps.append(dp)
		f.close()
		if deps != []:
			bb.data.setVar('RDEPENDS_%s' % pkg, " ".join(deps), d)
		bb.data.setVar('PKGV_' + pkg, libcver, d)

	do_split_packages(d, locales_dir, file_regex='(.*)', output_pattern='glibc-localedata-%s', description='locale definition for %s', hook=calc_locale_deps, extra_depends='')
	bb.data.setVar('PACKAGES', bb.data.getVar('PACKAGES', d) + ' glibc-gconv', d)

	f = open(os.path.join(bb.data.getVar('WORKDIR', d, 1), "SUPPORTED"), "r")
	supported = f.readlines()
	f.close()

	dot_re = re.compile("(.*)\.(.*)")

	# Collate the locales by base and encoding
	encodings = {}
	for l in supported:
		l = l[:-1]
		(locale, charset) = l.split(" ")
		m = dot_re.match(locale)
		if m:
			locale = m.group(1)
		if not encodings.has_key(locale):
			encodings[locale] = []
		encodings[locale].append(charset)

	def output_locale_source(name, locale, encoding):
		pkgname = 'locale-base-' + legitimize_package_name(name)

		bb.data.setVar('RDEPENDS_%s' % pkgname, 'localedef glibc-localedata-%s glibc-charmap-%s' % (legitimize_package_name(locale), legitimize_package_name(encoding)), d)
		rprovides = 'virtual-locale-%s' % legitimize_package_name(name)
		m = re.match("(.*)_(.*)", name)
		if m:
			rprovides += ' virtual-locale-%s' % m.group(1)
		bb.data.setVar('RPROVIDES_%s' % pkgname, rprovides, d)
		bb.data.setVar('PACKAGES', '%s %s' % (pkgname, bb.data.getVar('PACKAGES', d, 1)), d)
		bb.data.setVar('ALLOW_EMPTY_%s' % pkgname, '1', d)
		bb.data.setVar('pkg_postinst_%s' % pkgname, bb.data.getVar('locale_base_postinst', d, 1) % (locale, encoding, locale), d)
		bb.data.setVar('pkg_postrm_%s' % pkgname, bb.data.getVar('locale_base_postrm', d, 1) % (locale, encoding, locale), d)
		bb.data.setVar('PKGV_' + pkgname, libcver, d)

	def output_locale_binary(name, locale, encoding):
		target_arch = bb.data.getVar("TARGET_ARCH", d, 1)
		qemu = "qemu-%s" % target_arch
		pkgname = 'locale-base-' + legitimize_package_name(name)
		m = re.match("(.*)\.(.*)", name)
		if m:
			glibc_name = "%s.%s" % (m.group(1), m.group(2).lower().replace("-",""))
		else:
			glibc_name = name
		bb.data.setVar('RDEPENDS_%s' % pkgname, legitimize_package_name('glibc-binary-localedata-%s' % glibc_name), d)
		rprovides = 'virtual-locale-%s' % legitimize_package_name(name)
		m = re.match("(.*)_(.*)", name)
		if m:
			rprovides += ' virtual-locale-%s' % m.group(1)
		bb.data.setVar('RPROVIDES_%s' % pkgname, rprovides, d)
		bb.data.setVar('ALLOW_EMPTY_%s' % pkgname, '1', d)
		bb.data.setVar('PACKAGES', '%s %s' % (pkgname, bb.data.getVar('PACKAGES', d, 1)), d)
		bb.data.setVar('PKGV_' + pkgname, libcver, d)

		treedir = os.path.join(bb.data.getVar("WORKDIR", d, 1), "locale-tree")
		path = bb.data.getVar("PATH", d, 1)
		i18npath = os.path.join(treedir, datadir, "i18n")

		localedef_opts = "--force --old-style --no-archive --prefix=%s --inputfile=%s/i18n/locales/%s --charmap=%s %s" % (treedir, datadir, locale, encoding, name)
		cmd = "PATH=\"%s\" I18NPATH=\"%s\" %s -L %s %s/bin/localedef %s" % (path, i18npath, qemu, treedir, treedir, localedef_opts)
		bb.note("generating locale %s (%s)" % (locale, encoding))
		if os.system(cmd):
			raise bb.build.FuncFailed("localedef returned an error (command was %s)." % cmd)

	def output_locale(name, locale, encoding):
		output_locale_source(name, locale, encoding)

	# Reshuffle names so that UTF-8 is preferred over other encodings
	non_utf8 = []
	for l in encodings.keys():
		if len(encodings[l]) == 1:
			output_locale(l, l, encodings[l][0])
			if encodings[l][0] != "UTF-8":
				non_utf8.append(l)
		else:
			if "UTF-8" in encodings[l]:
				output_locale(l, l, "UTF-8")
				encodings[l].remove("UTF-8")
			else:
				non_utf8.append(l)
			for e in encodings[l]:
				output_locale('%s.%s' % (l, e), l, e)

	if non_utf8 != []:
		bb.note("the following locales are supported only in legacy encodings:")
		bb.note("  " + " ".join(non_utf8))
}

python package_do_split_locales() {
	if (bb.data.getVar('PACKAGE_NO_LOCALE', d, True) == '1'):
		bb.debug(1, "package requested not splitting locales")
		return

	packages = (bb.data.getVar('PACKAGES', d, True) or "").split()

	datadir = bb.data.getVar('datadir', d, True)
	if not datadir:
		bb.note("datadir not defined")
		return

	if 'glibc-locale' in packages:
		packages.remove('glibc-locale')

	localedir = os.path.join(datadir, 'locale')

	if not os.path.isdir(localedir):
		bb.debug(1, "No locale files in this package")
		return

	locales = os.listdir(localedir)
	for l in locales:
		ln = legitimize_package_name(l)
		pkg = 'glibc-locale-' + ln
		packages.append(pkg)
		bb.data.setVar('FILES_' + pkg, os.path.join(datadir, 'locale', l), d)
		bb.data.setVar('RDEPENDS_' + pkg, 'glibc virtual-locale-%s' % ln, d)
		bb.data.setVar('RPROVIDES_' + pkg, 'glibc-locale %s-translation' % ln, d)
		bb.data.setVar('DESCRIPTION_' + pkg, '%s translation for glibc' % l, d)
		bb.data.setVar('PKGV_' + pkg, bb.data.getVar('ARG_VER_LIBC', d, 1), d)

	bb.data.setVar('PACKAGES', ' '.join(packages), d)
}

python populate_packages_prepend () {
	if bb.data.getVar('DEBIAN_NAMES', d, 1):
		bb.data.setVar('PKG_glibc', 'libc6', d)
		bb.data.setVar('PKG_glibc-dev', 'libc6-dev', d)
		bb.data.setVar('PKG_libgcc', 'libgcc1', d)
		bb.data.setVar('PKG_libgcc-dev', 'libgcc1-dev', d)
	bb.build.exec_func('package_do_split_gconvs', d)
}

NATIVE_INSTALL_WORKS = "1"
