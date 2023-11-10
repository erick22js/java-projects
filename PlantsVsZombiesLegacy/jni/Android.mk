LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := PlantsVsZombiesLegacy
LOCAL_SRC_FILES := PlantsVsZombiesLegacy.cpp

include $(BUILD_SHARED_LIBRARY)
