// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   PrepareConfig.java

package com.icfcc.batch.validator;

import com.icfcc.batch.BatchContext;
import com.icfcc.batch.Constants;
import com.icfcc.foundation.configuration.XMLConfiguration;
import java.io.PrintStream;

public class PrepareConfig
{

    public PrepareConfig()
    {
        preCheckVersion = "1.0";
        checkFileName = true;
        checkFormat = true;
        outputPassFile = true;
        cryptFile = true;
        cryptMode = 1;
        keyMode = 1;
        loadSystemConfiguration();
    }

    public static PrepareConfig getInstance()
    {
        if(_instance == null)
            _instance = new PrepareConfig();
        return _instance;
    }

    private void loadSystemConfiguration()
    {
        if(bInitialized)
            return;
        try
        {
            String homeDir = BatchContext.getInstance().getBaseHome();
            if(homeDir == null)
            {
                homeDir = System.getProperty("Home");
                BatchContext.getInstance().setBaseHome(homeDir);
            }
            String configurationFile = homeDir + Constants.SYSTEM_PREPARE_CONFIG_NAME;
            XMLConfiguration config = new XMLConfiguration();
            config.loadConfiguration(configurationFile);
            try
            {
                String version = config.getString("version");
                if(version != null)
                    preCheckVersion = version;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6version\u9519\u8BEF");
            }
            try
            {
                String checkFileName = config.getString("CheckFileName");
                if(checkFileName == null || checkFileName.equalsIgnoreCase("true"))
                    this.checkFileName = true;
                else
                    this.checkFileName = false;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6CheckFileName\u9519\u8BEF");
            }
            try
            {
                String checkFormat = config.getString("CheckFormat");
                if(checkFormat == null || checkFormat.equalsIgnoreCase("true"))
                    this.checkFormat = true;
                else
                    this.checkFormat = false;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6CheckFormat\u9519\u8BEF");
            }
            try
            {
                String outputPassFile = config.getString("OutputPassFile");
                if(outputPassFile == null || outputPassFile.equalsIgnoreCase("true"))
                    this.outputPassFile = true;
                else
                    this.outputPassFile = false;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6OutputPassFile\u9519\u8BEF");
            }
            try
            {
                String cryptFile = config.getString("CryptFile");
                if(cryptFile == null || cryptFile.equalsIgnoreCase("true"))
                    this.cryptFile = true;
                else
                    this.cryptFile = false;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6CryptFile\u9519\u8BEF");
            }
            try
            {
                String cryptFile = config.getString("cryptMode");
                if(cryptFile == null || cryptFile.equalsIgnoreCase("2"))
                    cryptMode = 2;
                else
                    cryptMode = 1;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6cryptMode\u9519\u8BEF");
            }
            try
            {
                String cryptFile = config.getString("keyMode");
                if(cryptFile == null || cryptFile.equalsIgnoreCase("2"))
                    keyMode = 2;
                else
                    keyMode = 1;
            }
            catch(Exception ex)
            {
                System.out.println("\u3010PrepareConfig\u3011\u8BFB\u53D6keyMode\u9519\u8BEF");
            }
        }
        catch(Exception ex)
        {
            System.out.println("\u3010BatchConfig\u3011\u914D\u7F6E\u6587\u4EF6\u88C5\u8F7D\u51FA\u9519\uFF01");
            ex.printStackTrace();
            bInitialized = false;
        }
        bInitialized = true;
        return;
    }

    public boolean isCheckFileName()
    {
        return checkFileName;
    }

    public boolean isCheckFormat()
    {
        return checkFormat;
    }

    public boolean isCryptFile()
    {
        return cryptFile;
    }

    public boolean isOutputPassFile()
    {
        return outputPassFile;
    }

    public String getPreCheckVersion()
    {
        return preCheckVersion;
    }

    public int getCryptMode()
    {
        return cryptMode;
    }

    public int getKeyMode()
    {
        return keyMode;
    }

    private boolean bInitialized;
    private static PrepareConfig _instance = null;
    private String preCheckVersion;
    private boolean checkFileName;
    private boolean checkFormat;
    private boolean outputPassFile;
    private boolean cryptFile;
    private int cryptMode;
    private int keyMode;

}