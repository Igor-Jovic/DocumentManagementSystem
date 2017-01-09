package rs.ac.bg.fon.silab.dms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dms.core.model.Account;
import rs.ac.bg.fon.silab.dms.core.repository.AccountRepository;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createUser(Account account) throws BadRequestException {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new BadRequestException("Account with given username already exists.");
        }
        return accountRepository.saveAndFlush(account);
    }
}
