package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for the Communication class
 */
@Service
public class CommunicationService {

    @Autowired
    private LoginService loginService;

    /**
     * Get a communication by a username
     *
     * @param username an account's primary key
     * @return the communication instance
     * @throws MmssException
     */
    @Transactional
    public Communication getCommunicationByUsername(String username) {
        // Check for valid username
        AccountType account = loginService.getAccountByUsername(username);
        // Check for valid communication
        Communication communication = account.getCommunication();
        if (communication == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Communication not found");
        }
        return communication;
    }
}
