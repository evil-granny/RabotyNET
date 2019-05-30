package ua.softserve.ita.unittest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.softserve.ita.controller.ClaimController;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.service.ClaimService;
import ua.softserve.ita.service.CompanyService;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
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
    public void getClaimsByCompanyId()  {
        Claim mockClaim = Claim.builder()
                .claimId(1)
                .title("Claim")
                .description("Claim")
                .build();
        List<Claim> mockClaims = new LinkedList<>();
        mockClaims.add(mockClaim);

        when(claimService.findAllByCompanyId(eq(ID))).thenReturn(mockClaims);
        List<Claim> claimsByCompanyId = controller.findClaims(ID);

        assertEquals(mockClaims, claimsByCompanyId);

        verify(claimService, times(1)).findAllByCompanyId(eq(ID));
        verifyNoMoreInteractions(claimService, companyService);
    }

}
