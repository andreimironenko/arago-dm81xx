include Rules.make

DEFAULT_LINUXKERNEL_CONFIG=ti8168_evm_defconfig
DEFAULT_UBOOT_CONFIG=ti8168_evm_config
MIN_UBOOT_CONFIG=ti8168_evm_min_sd
# export cross compiler location in PATH
PATH := ${PATH}:${CSTOOL_PATH}

.PHONY:	components components_clean all clean help cmem cmem_clean cmem_install linux linux_clean linux_install install u-boot u-boot_clean u-boot_install c6accel c6accel_clean c6accel_install syslink syslink_clean syslink_install opengldemos opengldemos_clean opengldemos_install sademo sademo_clean sademo_install matrix matrix_clean matrix_install omx omx_clean omx_install

#==============================================================================
# Build components to enable all other build targets. 
#==============================================================================
components: linux cmem syslink

#==============================================================================
# Clean up the targets built by 'make all'.
#==============================================================================
components_clean: linux_clean syslink_clean cmem_clean 

#==============================================================================
# Build everything rebuildable.
#==============================================================================
all: components u-boot matrix omx

#==============================================================================
# Install everything
#==============================================================================
install: linux_install cmem_install syslink_install u-boot_install c6accel_install sademo_install matrix_install omx_install

#==============================================================================
# Clean up all targets.
#==============================================================================
clean: components_clean u-boot_clean sademo_clean matrix_clean omx_clean

#==============================================================================
# A help message target.
#==============================================================================
help:
	@echo
	@echo "Available build targets are:"
	@echo
	@echo "    components                   : Build the components for which a rebuild is necessary to enable all other build targets listed below. You must do this at least once upon installation prior to attempting the other targets."
	@echo "    components_clean             : Remove files generated by the 'components' target"
	@echo 
	@echo "    cmem                         : Build the CMEM kernel module"
	@echo "    cmem_clean                   : Remove generated cmem files."
	@echo "    cmem_install                 : Install cmemk module"
	@echo
	@echo "    syslink                      : Configure and build SYS Link for HLOS and HLOS without sample examples"
	@echo "    syslink_clean                : Remove generated SysLink files"
	@echo "    syslink_install              : Install HLOS and RTOS link files"
	@echo
	@echo "    syslink_hlos                 : Configure and build DSP Link for ARM"
	@echo "    syslink_hlos_clean           : Clean DSP Link for ARM"
	@echo 
	@echo "    syslink_rtos                 : Configure and build SysLink for RTOS"
	@echo "    syslink_rtos_clean           : Clean SysLink for RTOS"
	@echo 
	@echo
	@echo "    linux                        : Build Linux kernel uImage and module"
	@echo "    linux_clean                  : Remove generated Linux kernel files"
	@echo "    linux_install                : Install kernel binary and  modules"
	@echo
	@echo "    u-boot                       : Build the u-boot boot loader"
	@echo "    u-boot_clean                 : Remove generated u-boot files"
	@echo "    u-boot_install               : Install the u-boot image"
	@echo
	@echo "    c6accel                      : Build c6accel package"
	@echo "    c6accel_clean                : Remove generated c6accel files"
	@echo "    c6accel_install              : Install c6accel examples"
	@echo
	# @echo "    opengldemos                  : Build the OPENGL graphics SDK demos for $(PLATFORM)"
	# @echo "    opengldemos_clean            : Remove OPENGL graphics SDK demo files."
	# @echo "    opengldemos_install          : install OPENGL graphics SDK demo executables."
	@echo
	@echo "    sademo                       : Build the Signal Analyzer demo for $(PLATFORM)"
	@echo "    sademo_clean                 : Remove Signal Analyzer demo files."
	@echo "    sademo_install               : install Signal Analyzer demo executables."
	@echo
	@echo "    matrix                       : Build matrix application launcher"
	@echo "    matrix_clean                 : Remove all matrix files"
	@echo "    matrix_install               : Install matrix"
	@echo
	@echo "    omx                          : Build OMX and OMX IL Clients"
	@echo "    omx_clean                    : Remove OMX generated files"
	@echo "    omx_install                  : Install OMX IL Clients"
	@echo
	@echo "    all                          : Rebuild everything"
	@echo "    clean                        : Remove all generated files"
	@echo
	@echo "    install                      : Install all the targets in "
	@echo "                            $(EXEC_DIR)"
	@echo

#==============================================================================
# Build the Linux kernel. Also, an explicit cleanup target is defined.
#==============================================================================
linux:
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) ARCH=arm CROSS_COMPILE=$(CSTOOL_PREFIX) $(DEFAULT_LINUXKERNEL_CONFIG)
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) ARCH=arm CROSS_COMPILE=$(CSTOOL_PREFIX) uImage
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) ARCH=arm CROSS_COMPILE=$(CSTOOL_PREFIX) modules
linux_clean:
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) mrproper
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) ARCH=arm CROSS_COMPILE=$(CSTOOL_PREFIX) clean

linux_install:
	install -d $(EXEC_DIR)/boot
	install  $(LINUXKERNEL_INSTALL_DIR)/arch/arm/boot/uImage $(EXEC_DIR)/boot
	install  $(LINUXKERNEL_INSTALL_DIR)/vmlinux $(EXEC_DIR)/boot
	install  $(LINUXKERNEL_INSTALL_DIR)/System.map $(EXEC_DIR)/boot
	$(MAKE) -C $(LINUXKERNEL_INSTALL_DIR) ARCH=arm CROSS_COMPILE=$(CSTOOL_PREFIX) INSTALL_MOD_PATH=$(EXEC_DIR)/ modules_install

#==============================================================================
# Build u-boot. Also, an explicit cleanup target is defined.
#==============================================================================
u-boot-min:
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) $(MIN_UBOOT_CONFIG)
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) u-boot.ti
	cd $(PSP_INSTALL_DIR)/u-boot-*; mv u-boot.min.sd MLO; cd -
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) distclean

u-boot-min_clean:
	rm -f $(PSP_INSTALL_DIR)/u-boot-*/MLO

u-boot: u-boot-min
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) $(DEFAULT_UBOOT_CONFIG)
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) u-boot.ti

u-boot_clean: u-boot-min_clean
	$(MAKE) -C $(PSP_INSTALL_DIR)/u-boot-* CROSS_COMPILE=$(CSTOOL_PREFIX) distclean

u-boot_install:
	install -d $(EXEC_DIR)/boot
	install $(PSP_INSTALL_DIR)/u-boot-*/MLO $(EXEC_DIR)/boot
	install $(PSP_INSTALL_DIR)/u-boot-*/u-boot.bin $(EXEC_DIR)/boot
	install $(PSP_INSTALL_DIR)/u-boot-*/u-boot.map $(EXEC_DIR)/boot

#==============================================================================
# Build the CMEM kernel module for the configured platform, and make sure the
# kernel_binaries directory is kept in sync. Also, an explicit cleanup target
# is defined.
#==============================================================================
cmem:
	$(MAKE) -C $(CMEM_INSTALL_DIR)/packages/ti/sdo/linuxutils/cmem/src/interface ../../lib/cmem.a470MV
	$(MAKE) -C $(CMEM_INSTALL_DIR)/packages/ti/sdo/linuxutils/cmem/src/module

cmem_clean:
	$(MAKE) -C $(CMEM_INSTALL_DIR)/packages/ti/sdo/linuxutils/cmem/src/module clean
	$(MAKE) -C $(CMEM_INSTALL_DIR)/packages/ti/sdo/linuxutils/cmem/src/interface clean

cmem_install:
	install -d $(EXEC_DIR)/cmem
	install $(CMEM_INSTALL_DIR)/packages/ti/sdo/linuxutils/cmem/src/module/cmemk.ko $(EXEC_DIR)/cmem


###############################################################################
# Build SYSLINK
###############################################################################
SYSLINK_ROOT=${SYSLINK_INSTALL_DIR}
SYSLINK_XDCPATH="${IPC_INSTALL_DIR}/packages;${SYSBIOS_INSTALL_DIR}/packages"

syslink_cfg:
	sed -i \
        -e s:^C64.rootDir.*:C64.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^C64P_COFF.rootDir.*:C64P_COFF.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^C64P_ELF.rootDir.*:C64P_ELF.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^C67P_ELF.rootDir.*:C67P_ELF.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^C674_ELF.rootDir.*:C674_ELF.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^C674_COFF.rootDir.*:C674_COFF.rootDir\ =\ \"${CODEGEN_INSTALL_DIR}\":g \
        -e s:^Arm9.rootDir.*:Arm9.rootDir\ =\ \"${TMS470_CODEGEN_INSTALL_DIR}\":g \
        -e s:^M3_ELF.rootDir.*:M3_ELF.rootDir\ =\ \"${TMS470_CODEGEN_INSTALL_DIR}\":g \
	-e s:^A8_ELF.rootDir.*:A8_ELF.rootDir\ =\ \"${TMS470_CODEGEN_INSTALL_DIR}\":g \
        -e s:C64P_COFF,://C64P_COFF:g \
        -e s:C64P_ELF,://C64P_ELF:g \
        -e s:M3_ELF,://M3_ELF:g \
        -e s:A8_ELF$$://A8_ELF:g \
        ${SYSLINK_INSTALL_DIR}/config.bld

syslink_hlos: 
	${MAKE} -C ${SYSLINK_INSTALL_DIR}/ti/syslink/utils/hlos/knl/Linux/ \
		SYSLINK_ROOT=${SYSLINK_INSTALL_DIR} \
		SYSLINK_PKGPATH="${IPC_INSTALL_DIR}/packages;${SYSLINK_INSTALL_DIR}" \
		ARCH=arm \
		CROSS_COMPILE=${CSTOOL_PREFIX} \
		TOOLCHAIN_PREFIX=${CSTOOL_PREFIX} \
		SYSLINK_PLATFORM=TI81XX \
		KDIR=$(LINUXKERNEL_INSTALL_DIR)
	${MAKE} -C ${SYSLINK_INSTALL_DIR}/ti/syslink/utils/hlos/usr/Linux/ \
		SYSLINK_ROOT=${SYSLINK_INSTALL_DIR} \
		SYSLINK_PKGPATH="${IPC_INSTALL_DIR}/packages;${SYSLINK_INSTALL_DIR}" \
		ARCH=arm \
		CROSS_COMPILE=${CSTOOL_PREFIX} \
		TOOLCHAIN_PREFIX=${CSTOOL_PREFIX} \
		SYSLINK_PLATFORM=TI81XX \
		KDIR=$(LINUXKERNEL_INSTALL_DIR)

syslink_hlos_clean: 
	${MAKE} -C ${SYSLINK_INSTALL_DIR}/ti/syslink/utils/hlos/knl/Linux/ \
		SYSLINK_ROOT=${SYSLINK_INSTALL_DIR} \
		SYSLINK_PKGPATH="${IPC_INSTALL_DIR}/packages;${SYSLINK_INSTALL_DIR}" \
		ARCH=arm \
		CROSS_COMPILE=${CSTOOL_PREFIX} \
		TOOLCHAIN_PREFIX=${CSTOOL_PREFIX} \
		SYSLINK_PLATFORM=TI81XX \
		KDIR=$(LINUXKERNEL_INSTALL_DIR) clean
	${MAKE} -C ${SYSLINK_INSTALL_DIR}/ti/syslink/utils/hlos/usr/Linux/ \
		SYSLINK_ROOT=${SYSLINK_INSTALL_DIR} \
		SYSLINK_PKGPATH="${IPC_INSTALL_DIR}/packages;${SYSLINK_INSTALL_DIR}" \
		ARCH=arm \
		CROSS_COMPILE=${CSTOOL_PREFIX} \
		TOOLCHAIN_PREFIX=${CSTOOL_PREFIX} \
		SYSLINK_PLATFORM=TI81XX \
		KDIR=$(LINUXKERNEL_INSTALL_DIR) clean

syslink_rtos: syslink_cfg
	XDCPATH=${SYSLINK_XDCPATH} \
		SYSLINK_ROOT=${SYSLINK_INSTALL_DIR} \
		${XDC_INSTALL_DIR}/xdc -P \
		`${XDC_INSTALL_DIR}/bin/xdcpkg ${SYSLINK_INSTALL_DIR}/ti/syslink | grep -v samples`

syslink_rtos_clean: syslink_cfg
	XDCPATH=${SYSLINK_XDCPATH} ${XDC_INSTALL_DIR}/xdc clean -PR ${SYSLINK_INSTALL_DIR}/ti/syslink/.

syslink_clean: syslink_hlos_clean syslink_rtos_clean

syslink: syslink_hlos syslink_rtos

syslink_install:
	install -d $(EXEC_DIR)/syslink
	install ${SYSLINK_INSTALL_DIR}/ti/syslink/lib/modules/TI81XX/syslink.ko $(EXEC_DIR)/syslink
	cp ${SYSLINK_INSTALL_DIR}/ti/syslink/lib/syslink.a $(EXEC_DIR)/syslink

##############################################################################
#  Build c6accel
##############################################################################
c6accel:
	XDCPATH=${C6ACCEL_XDCPATH} \
	$(MAKE) -C $(C6ACCEL_INSTALL_DIR) \
	PLATFORM="TI816X" \
	PLATFORM_XDC="ti.platforms.evmTI816X" \
	ARM_ISA=armv7-a \
	DSP_ISA=674 \
	CE_INSTALL_DIR="$(CE_INSTALL_DIR)" \
	XDAIS_INSTALL_DIR="$(XDAIS_INSTALL_DIR)" \
	LINK_INSTALL_DIR="$(SYSLINK_INSTALL_DIR)" \
	IPC_INSTALL_DIR="${IPC_INSTALL_DIR}" \
	OSAL_INSTALL_DIR="${OSAL_INSTALL_DIR}" \
	CMEM_INSTALL_DIR="$(CMEM_INSTALL_DIR)" \
	EDMA3_LLD_INSTALL_DIR="$(EDMA3_LLD_INSTALL_DIR)" \
	CODEGEN_INSTALL_DIR="$(CODEGEN_INSTALL_DIR)" \
	XDC_INSTALL_DIR="$(XDC_INSTALL_DIR)" \
	FC_INSTALL_DIR="$(FC_INSTALL_DIR)" \
	BIOS_INSTALL_DIR="$(SYSBIOS_INSTALL_DIR)" \
	C6ACCEL_INSTALL_DIR="$(C6ACCEL_INSTALL_DIR)" \
	CSTOOL_DIR="$(CSTOOL_DIR)" \
	CSTOOL_PREFIX="$(CSTOOL_PREFIX)" \
	all

c6accel_clean:
	$(MAKE) -C $(C6ACCEL_INSTALL_DIR) \
	PLATFORM="TI816X" \
	CE_INSTALL_DIR="$(CE_INSTALL_DIR)" \
	XDAIS_INSTALL_DIR="$(XDAIS_INSTALL_DIR)" \
	LINK_INSTALL_DIR="$(LINK_INSTALL_DIR)" \
	IPC_INSTALL_DIR="${IPC_INSTALL_DIR}" \
	OSAL_INSTALL_DIR="${OSAL_INSTALL_DIR}" \
	CMEM_INSTALL_DIR="$(CMEM_INSTALL_DIR)" \
	EDMA3_LLD_INSTALL_DIR="$(EDMA3_LLD_INSTALL_DIR)" \
	CODEGEN_INSTALL_DIR="$(CODEGEN_INSTALL_DIR)" \
	XDC_INSTALL_DIR="$(XDC_INSTALL_DIR)" \
	FC_INSTALL_DIR="$(FC_INSTALL_DIR)" \
	BIOS_INSTALL_DIR="$(BIOS_INSTALL_DIR)" \
	SERVER_INSTALL_DIR="$(CODEC_INSTALL_DIR)" \
	C6ACCEL_INSTALL_DIR="$(C6ACCEL_INSTALL_DIR)" \
	CSTOOL_DIR="$(CSTOOL_DIR)" \
	CSTOOL_PREFIX="$(CSTOOL_PREFIX)" \
	clean

c6accel_install:
	$(MAKE) -C $(C6ACCEL_INSTALL_DIR) \
	PLATFORM="TI816X" \
	CMEM_INSTALL_DIR="$(CMEM_INSTALL_DIR)" \
	LINK_INSTALL_DIR="$(SYSLINK_INSTALL_DIR)" \
	C6ACCEL_INSTALL_DIR="$(C6ACCEL_INSTALL_DIR)" \
	EXEC_DIR_C6ACCEL="$(EXEC_DIR)/c6accel" \
	install

################################################################################
# Build openGL demos
################################################################################
# opengldemos:
# 	$(MAKE) -C $(GRAPHICS_INSTALL_DIR) \
# 		BUILD=release \
# 		OMAPES=6.x \
# 		GRAPHICS_INSTALL_DIR=${GRAPHICS_INSTALL_DIR} \
# 		CSTOOL_DIR=${CSTOOL_DIR} \
# 		KERNEL_INSTALL_DIR=${KERNEL_INSTALL_DIR} \
# 		all

# opengldemos_clean:
# 	$(MAKE) -C $(GRAPHICS_INSTALL_DIR) \
# 		BUILD=release \
# 		OMAPES=6.x \
# 		GRAPHICS_INSTALL_DIR=${GRAPHICS_INSTALL_DIR} \
# 		CSTOOL_DIR=${CSTOOL_DIR} \
# 		KERNEL_INSTALL_DIR=${KERNEL_INSTALL_DIR} \
# 		clean

# opengldemos_install:
# 	mkdir -p ${EXEC_DIR}/opengl-demos/etc/init.d
# 	$(MAKE) -C $(GRAPHICS_INSTALL_DIR) \
# 		BUILD=release \
# 		OMAPES=6.x \
# 		GRAPHICS_INSTALL_DIR=${GRAPHICS_INSTALL_DIR} \
# 		CSTOOL_DIR=${CSTOOL_DIR} \
# 		KERNEL_INSTALL_DIR=${KERNEL_INSTALL_DIR} \
# 		TARGETFS_INSTALL_DIR=$(EXEC_DIR)/opengl-demos \
# 		install
# 	for file in `find $(GRAPHICS_INSTALL_DIR)/gfx_rel_es3.x -name "*.ko"`; do \
# 	    install $${file} $(EXEC_DIR)/opengl-demos; \
# 	done


################################################################################
# Build Signal Analyzer demo
################################################################################

sademo:
	. $(LINUX_DEVKIT_DIR)/environment-setup; cd $(SADEMO_INSTALL_DIR); qmake; cd -
	$(MAKE) -C $(SADEMO_INSTALL_DIR)/app \
		ROOTDIR=$(DVSDK_INSTALL_DIR) \
	        PLATFORM="TI816X" \
		PLATFORM_XDC=ti.platforms.evmDM8168 \
		FFT_QT_GUI_INSTALL_DIR=$(SADEMO_INSTALL_DIR)
	. $(LINUX_DEVKIT_DIR)/environment-setup; $(MAKE) -C $(SADEMO_INSTALL_DIR)

sademo_clean:
	$(MAKE) -C $(SADEMO_INSTALL_DIR)/app PLATFORM="TI816X" clean
	if [ -e $(SADEMO_INSTALL_DIR)/Makefile ]; then $(MAKE) -C $(SADEMO_INSTALL_DIR) distclean; fi

sademo_install: c6accel_install
	install -d $(EXEC_DIR)/signal-analyzer
	install $(SADEMO_INSTALL_DIR)/sigan_demo_app $(EXEC_DIR)/signal-analyzer
	install $(SADEMO_INSTALL_DIR)/app/scripts/bin/fft_demo_8khz.pcm $(EXEC_DIR)/signal-analyzer
	cd $(EXEC_DIR)/signal-analyzer; ln -s ../c6accel/test_files; cd -
	cd $(EXEC_DIR)/signal-analyzer; ln -s ../c6accel/ti816x.xe674; cd -

################################################################################
# Build Martix GUI Application Launcher
################################################################################
matrix:
	. $(DVSDK_INSTALL_DIR)/linux-devkit/environment-setup ; cd demos/matrix-gui-e* ; qmake ; $(MAKE) PLATFORM=ti816x

matrix_clean:
	. $(DVSDK_INSTALL_DIR)/linux-devkit/environment-setup;  cd demos/matrix-gui-e* ; qmake ; $(MAKE) distclean

matrix_install:
	install -d $(EXEC_DIR)/matrix
	install -m 0755 $(DVSDK_INSTALL_DIR)/demos/matrix-gui-e*/matrix_gui $(EXEC_DIR)/matrix
	cp -ar $(DVSDK_INSTALL_DIR)/demos/matrix-gui-e*/ti816x/bin \
        $(EXEC_DIR)/matrix/
	cp -ar $(DVSDK_INSTALL_DIR)/demos/matrix-gui-e*/ti816x/html \
        $(EXEC_DIR)/matrix/
	cp -ar $(DVSDK_INSTALL_DIR)/demos/matrix-gui-e*/images/ \
        $(EXEC_DIR)/matrix/


################################################################################
# Build OMX and IL Client Applications 
################################################################################

omx_vc:
	make -C $(OMX_INSTALL_DIR)/packages vc3 \
		ROOTDIR=$(OMX_INSTALL_DIR)/packages \
		EXTERNAL_SW_ROOT=$(DVSDK_INSTALL_DIR) \
		INTERNAL_SW_ROOT=$(OMX_INSTALL_DIR)/packages \
		bios_PATH=$(SYSBIOS_INSTALL_DIR) \
		fc_PATH=$(FC_INSTALL_DIR) \
		osal_PATH=$(OSAL_INSTALL_DIR) \
		xdais_PATH=$(XDAIS_INSTALL_DIR) \
		linuxutils_PATH=$(LINUXUTILS_INSTALL_DIR) \
		ce_PATH=$(CE_INSTALL_DIR) \
		h264enc_PATH=$(CODEC_INSTALL_DIR) \
		h264dec_PATH=$(CODEC_INSTALL_DIR) \
		hdvicp20api_PATH=$(IVAHD_HDVICP20API_INSTALL_DIR) \
		ipc_PATH=$(IPC_INSTALL_DIR) \
		syslink_PATH=$(SYSLINK_INSTALL_DIR) \
		xdc_PATH=$(XDC_INSTALL_DIR) \
		hdvpss_PATH=$(HDVPSS_INSTALL_DIR) \
		CODEGEN_PATH_M3=$(TMS470_CODEGEN_INSTALL_DIR) \
		CODEGEN_PATH_A8=$(CSTOOL_DIR) \
		DEST_ROOT=$(OMX_INSTALL_DIR)/rebuilt-binaries

omx_vs:
	make -C $(OMX_INSTALL_DIR)/packages vs2 \
		ROOTDIR=$(OMX_INSTALL_DIR)/packages \
		EXTERNAL_SW_ROOT=$(DVSDK_INSTALL_DIR) \
		INTERNAL_SW_ROOT=$(OMX_INSTALL_DIR)/packages \
		bios_PATH=$(SYSBIOS_INSTALL_DIR) \
		fc_PATH=$(FC_INSTALL_DIR) \
		osal_PATH=$(OSAL_INSTALL_DIR) \
		xdais_PATH=$(XDAIS_INSTALL_DIR) \
		linuxutils_PATH=$(LINUXUTILS_INSTALL_DIR) \
		ce_PATH=$(CE_INSTALL_DIR) \
		h264enc_PATH=$(CODEC_INSTALL_DIR) \
		h264dec_PATH=$(CODEC_INSTALL_DIR) \
		hdvicp20api_PATH=$(IVAHD_HDVICP20API_INSTALL_DIR) \
		ipc_PATH=$(IPC_INSTALL_DIR) \
		syslink_PATH=$(SYSLINK_INSTALL_DIR) \
		xdc_PATH=$(XDC_INSTALL_DIR) \
		hdvpss_PATH=$(HDVPSS_INSTALL_DIR) \
		CODEGEN_PATH_M3=$(TMS470_CODEGEN_INSTALL_DIR) \
		CODEGEN_PATH_A8=$(CSTOOL_DIR) \
		DEST_ROOT=$(OMX_INSTALL_DIR)/rebuilt-binaries

omx: linux syslink omx_vc omx_vs

omx_clean:
	make -C $(OMX_INSTALL_DIR)/packages cleanall \
		ROOTDIR=$(OMX_INSTALL_DIR)/packages \
		EXTERNAL_SW_ROOT=$(DVSDK_INSTALL_DIR) \
		INTERNAL_SW_ROOT=$(OMX_INSTALL_DIR)/packages \
		bios_PATH=$(SYSBIOS_INSTALL_DIR) \
		fc_PATH=$(FC_INSTALL_DIR) \
		osal_PATH=$(OSAL_INSTALL_DIR) \
		xdais_PATH=$(XDAIS_INSTALL_DIR) \
		linuxutils_PATH=$(LINUXUTILS_INSTALL_DIR) \
		ce_PATH=$(CE_INSTALL_DIR) \
		h264enc_PATH=$(CODEC_INSTALL_DIR) \
		h264dec_PATH=$(CODEC_INSTALL_DIR) \
		hdvicp20api_PATH=$(IVAHD_HDVICP20API_INSTALL_DIR) \
		ipc_PATH=$(IPC_INSTALL_DIR) \
		syslink_PATH=$(SYSLINK_INSTALL_DIR) \
		xdc_PATH=$(XDC_INSTALL_DIR) \
		hdvpss_PATH=$(HDVPSS_INSTALL_DIR) \
		CODEGEN_PATH_M3=$(TMS470_CODEGEN_INSTALL_DIR) \
		CODEGEN_PATH_A8=$(CSTOOL_DIR) \
		DEST_ROOT=$(OMX_INSTALL_DIR)/rebuilt-binaries
	rm -rf $(OMX_INSTALL_DIR)/rebuild-binaries/

omx_install:
	install -d $(EXEC_DIR)/omx
	install -m 0755 $(OMX_INSTALL_DIR)/rebuilt-binaries/* $(EXEC_DIR)/omx
