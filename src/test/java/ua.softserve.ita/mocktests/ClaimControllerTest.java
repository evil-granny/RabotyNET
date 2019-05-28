package ua.softserve.ita.mocktests;


import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.ita.controller.ClaimController;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ClaimControllerTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private ClaimService claimService;

    private ClaimController controller;

    private static final long ID = 1;

    @Before
    public void setUp() {
        this.controller = new ClaimController(companyService, claimService);
    }

    @Test
    public void getClaimsById()  {
        Claim mockClaim = Claim.builder()
                .claimId(1)
                .title("Claim")
                .description("Claim")
                .build();

        when(claimService.findById(eq(ID))).thenReturn(Optional.of(mockClaim));
        Claim claimById = controller.findClaimById(ID);

        assertEquals(mockClaim, claimById);

        verify(claimService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(claimService, companyService);
    }

    @Test(expected = ResourceNotFoundException.class )
    public void getProductsClaimServiceThrowsException()  {
        when(claimService.findById(eq(ID))).thenThrow(new ResourceNotFoundException("Claim not found with id " + ID));
        controller.findClaimById(ID);
        verify(claimService, times(1)).findById(eq(ID));
        verifyNoMoreInteractions(claimService, companyService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getProductsWithNegativeId()  {
      controller.findClaimById(-5);
    }

}
