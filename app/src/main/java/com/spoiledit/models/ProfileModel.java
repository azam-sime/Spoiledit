package com.spoiledit.models;

import androidx.annotation.NonNull;

public class ProfileModel extends UserModel {
    public static ProfileModel fromUserModel(UserModel userModel) {
        ProfileModel profileModel = new ProfileModel();
        profileModel.setActivationKey(userModel.getActivationKey());
        profileModel.setCapKey(userModel.getCapKey());
        profileModel.setCaps(userModel.getCaps());
        profileModel.setDisplayName(userModel.getDisplayName());
        profileModel.setEmail(userModel.getEmail());
        profileModel.setPhone(userModel.getPhone());
        profileModel.setAddress(userModel.getAddress());
        profileModel.setDob(userModel.getDob());
        profileModel.setId(userModel.getId());
        profileModel.setLogin(userModel.getLogin());
        profileModel.setNiceName(userModel.getNiceName());
        profileModel.setPassword(userModel.getPassword());
        profileModel.setRegistered(userModel.getRegistered());
        profileModel.setStatus(userModel.getStatus());
        profileModel.setUrl(userModel.getUrl());

        return profileModel;
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setActivationKey(this.getActivationKey());
        userModel.setCapKey(this.getCapKey());
        userModel.setCaps(this.getCaps());
        userModel.setDisplayName(this.getDisplayName());
        userModel.setEmail(this.getEmail());
        userModel.setPhone(this.getPhone());
        userModel.setAddress(this.getAddress());
        userModel.setDob(this.getDob());
        userModel.setId(this.getId());
        userModel.setLogin(this.getLogin());
        userModel.setNiceName(this.getNiceName());
        userModel.setPassword(this.getPassword());
        userModel.setRegistered(this.getRegistered());
        userModel.setStatus(this.getStatus());
        userModel.setUrl(this.getUrl());
        
        return userModel;
    }
}
