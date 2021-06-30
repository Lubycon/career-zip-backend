package com.careerzip.domain.account.entity;

import com.careerzip.domain.acquisition.entity.Acquisition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.acquisition.AcquisitionFactory.createAcquisition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("다른 Account인지 판정하는 메서드 테스트")
    void isDifferentAccountTest() {
        // given
        Account account = createMember();
        Long differentId = account.getId() + 1L;

        // when
        boolean different = account.isDifferentAccount(differentId);

        // then
        assertThat(different).isTrue();
    }

    @Test
    @DisplayName("submitCount 증가 메서드 테스트")
    void addSubmitCountTest() {
        // given
        Account account = createMember();
        int beforeCount = account.getSubmitCount();

        // when
        Account updatedAccount = account.addSubmitCount();

        // then
        assertThat(updatedAccount.getSubmitCount()).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("Acquisition 추가 메서드 테스트")
    void addAcquisitionTest() {
        // given
        Account account = createMember();
        Acquisition acquisition = createAcquisition();

        // when
        Account updatedAccount = account.addAcquisition(acquisition);

        // then
        assertThat(updatedAccount.getAcquisition()).usingRecursiveComparison().isEqualTo(acquisition);
    }

    @Test
    @DisplayName("프로필 업데이트 메서드 테스트")
    void updateTest() {
        // given
        Account account = createMember();
        String newName = "New Name";
        String newEmail = "New Email";

        // when
        account.update(newName, newEmail);

        // then
        assertThat(account.getName()).isEqualTo(newName);
        assertThat(account.getEmail()).isEqualTo(newEmail);
    }
}
