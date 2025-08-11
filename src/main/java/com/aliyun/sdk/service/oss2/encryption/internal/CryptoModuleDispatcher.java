package com.aliyun.sdk.service.oss2.encryption.internal;


import com.aliyun.sdk.service.oss2.encryption.CryptoConfiguration;
import com.aliyun.sdk.service.oss2.encryption.EncryptionMaterials;

public class CryptoModuleDispatcher implements CryptoModule {
    private final CryptoModuleAesCtr cryptoModule;

    public CryptoModuleDispatcher(OSSDirect ossDirect,
                                  EncryptionMaterials encryptionMaterials,
                                  CryptoConfiguration cryptoConfig) {
        cryptoConfig = cryptoConfig.clone();
        this.cryptoModule = new CryptoModuleAesCtr(ossDirect, encryptionMaterials, cryptoConfig);
    }
}
