package uk.gov.hmcts.pdda.business.services.pdda;

import com.jcraft.jsch.Session;

/**
 * <p>
 * Title: Sftp Config.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2023
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 * @version 1.0
 */
public class SftpConfig {
    String username;
    String password;
    String remoteFolder;
    String host;
    Integer port;
    String errorMsg;
    Session session;
    
    void setSession(Session session) {
        this.session = session;
    }
}
