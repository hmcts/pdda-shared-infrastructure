package uk.gov.hmcts.pdda.business.services.pdda;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * <p>
 * Title: PDDA SFTP Helper.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2022
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Mark Harris
 * @version 1.0
 */
public class PddaSftpHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PddaSftpHelper.class);
    private static final String LOG_CALLED = " called";

    private String methodName;
    private JSch jsch;

    public void sftpFiles(Session session, String remoteFolder, Map<String, InputStream> files)
        throws JSchException, SftpException {
        methodName = "sftpFiles()";
        LOG.debug(methodName + LOG_CALLED);

        try {
            // Create a channel
            ChannelSftp sftpChannel = PddaSftpUtil.createChannel(session);

            // Validate the remote folder
            PddaSftpUtil.validateFolder(sftpChannel, remoteFolder);

            // Transfer Files
            PddaSftpUtil.transferFiles(sftpChannel, remoteFolder, files);

            // Close the channel
            sftpChannel.exit();

        } catch (JSchException | SftpException ex) {
            LOG.error(ex.getMessage());
            throw ex;
        }
    }

    private JSch getJSch() {
        if (jsch == null) {
            jsch = new JSch();
        }
        return jsch;
    }

    public Session createSession(String username, String password, String host, Integer port)
        throws JSchException {
        methodName = "createSession()";
        LOG.debug(methodName + LOG_CALLED);

        Session session = getJSch().getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();
        return session;
    }

    public void sftpDeleteFile(Session session, String remoteFolder, String filename)
        throws JSchException, SftpException {
        methodName = "sftpDeleteFile()";
        LOG.debug(methodName + LOG_CALLED);

        try {
            // Create a channel
            ChannelSftp sftpChannel = PddaSftpUtil.createChannel(session);

            // Validate the remote folder
            PddaSftpUtil.validateFolder(sftpChannel, remoteFolder);

            // Delete File
            sftpChannel.rm(remoteFolder + filename);

            // Close the channel
            sftpChannel.exit();

        } catch (JSchException | SftpException ex) {
            LOG.error(ex.getMessage());
            throw ex;
        }
    }

    public Map<String, String> sftpFetch(Session session, String remoteFolder,
        SftpValidation validation) throws JSchException, SftpException {
        methodName = "sftpFetch()";
        LOG.debug(methodName + LOG_CALLED);

        try {
            // Create a channel
            ChannelSftp sftpChannel = PddaSftpUtil.createChannel(session);

            // Validate the remote folder
            PddaSftpUtil.validateFolder(sftpChannel, remoteFolder);

            // Fetch Files
            Map<String, String> files = fetchFiles(sftpChannel, remoteFolder, validation);

            // Close the channel
            sftpChannel.exit();

            return files;
        } catch (JSchException | SftpException ex) {
            LOG.error(ex.getMessage());
            throw ex;
        }
    }

    private List<String> listFilesInFolder(ChannelSftp sftpChannel, String folder,
        SftpValidation validation) throws SftpException {
        methodName = "listFilesInFolder()";
        LOG.debug(methodName + LOG_CALLED);
        // Get the directory contents from the OS
        @SuppressWarnings("unchecked")
        List<String> filesInFolder = new ArrayList<>(sftpChannel.ls(folder));
        LOG.debug("No Of Files In Folder: " + filesInFolder.size());
        // Validate whether to include directories, etc
        List<String> results = validation.validateFilesInFolder(filesInFolder);
        LOG.debug("No Of Files (excluding any folders): " + (results != null ? results.size() : 0));
        // User validation that can be overridden in the calling class
        results = validation.validateFilenames(results);
        LOG.debug("No Of Files (after filters applied): " + (results != null ? results.size() : 0));
        // Return the end list of files required
        return results;
    }

    private Map<String, String> fetchFiles(ChannelSftp sftpChannel, String remoteFolder,
        SftpValidation validation) throws SftpException {
        methodName = "fetchFiles()";
        LOG.debug(methodName + LOG_CALLED);

        Map<String, String> files = new ConcurrentHashMap<>();
        try {
            List<String> listOfFilesInFolder =
                listFilesInFolder(sftpChannel, remoteFolder, validation);
            if (listOfFilesInFolder != null) {
                for (String filename : listOfFilesInFolder) {
                    try (InputStream inputStream = sftpChannel.get(remoteFolder + filename)) {
                        String fileContents = getFileContents(filename, inputStream);
                        files.put(filename, fileContents);
                    } catch (IOException e) {
                        LOG.error("Error reading: {}", filename);
                    }
                }
            }
            return files;
        } catch (SftpException ex) {
            LOG.error(ex.getMessage());
            throw ex;
        }
    }

    private String getFileContents(String filename, InputStream inputStream) {
        String fileContents = null;
        try (InputStreamReader fileReader = new InputStreamReader(inputStream)) {
            try (BufferedReader reader = new BufferedReader(fileReader)) {
                StringBuilder stringBuilder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
                fileContents = stringBuilder.toString();
                LOG.debug("File contents read");
            }
        } catch (IOException e) {
            LOG.error("Error reading: {}", filename);
        }
        return fileContents;
    }
}
