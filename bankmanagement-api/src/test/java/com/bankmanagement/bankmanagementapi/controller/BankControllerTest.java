package com.bankmanagement.bankmanagementapi.controller;

import com.bankmanagement.bankmanagementapi.dto.BankDTO;
import com.bankmanagement.bankmanagementapi.dto.BranchDTO;
import com.bankmanagement.bankmanagementapi.exception.BankDetailsNotFound;
import com.bankmanagement.bankmanagementapi.service.BankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Test
    public void testGetBanks() throws Exception {
        List<BankDTO> bankDTOList = new ArrayList<>();
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Bangalore");

        List<BranchDTO> branchDTOList = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName("Electronic City");
        branchDTO.setBranchAddress("Electronic City");

        branchDTOList.add(branchDTO);
        bankDTO.setBranchDto(branchDTOList);
        bankDTOList.add(bankDTO);

        when(bankService.findAllBanks()).thenReturn(bankDTOList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

/*        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());*/

        // compare the body
//        mockMvc.perform(requestBuilder).andExpect(jsonPath("$", Matchers.hasSize(1))).andDo(print());

        // compare the status code
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testBank_When_Empty() throws Exception{

        List<BankDTO> bankDTOList = new ArrayList<>();

        when(bankService.findAllBanks()).thenThrow(new BankDetailsNotFound());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void testBank_When_NotNull() throws Exception{

        when(bankService.findAllBanks()).thenThrow(new NullPointerException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    @Test
    public void findBankDetailsById_When_Id_is_NotNull() throws Exception{

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Nagercoil");

        List<BranchDTO> branchDTOList = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchAddress("Nagercoil");
        branchDTO.setBranchName("Main Nagercoil");

        branchDTOList.add(branchDTO);

        bankDTO.setBranchDto(branchDTOList);

        when(bankService.findBankDetailsById(anyInt())).thenReturn(bankDTO);

        RequestBuilder  requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/12")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void findBankDetailsById_When_Id_is_Null() throws Exception{

        when(bankService.findBankDetailsById(anyInt())).thenThrow(new NullPointerException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/12")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    @Test
    public void getByName_When_it_isNotNull() throws Exception{

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Nagercoil");

        List<BranchDTO> branchDTOList = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName("Main Nagercoil");
        branchDTO.setBranchAddress("Nagercoil");

        branchDTOList.add(branchDTO);
        bankDTO.setBranchDto(branchDTOList);

        when(bankService.getByName(anyString())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/banks/getName?name=SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void When_Saving_theBank_NotNull() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Nagercoil");

        List<BranchDTO> branchDTOList = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName("Main Nagercoil");
        branchDTO.setBranchAddress("Nagercoil");

        branchDTOList.add(branchDTO);

        bankDTO.setBranchDto(branchDTOList);

        when(bankService.saveBank(any())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/banks")
                .content(asJsonString(bankDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void When_Updating_theBankDetails_NotNull() throws Exception {

        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("SBI");
        bankDTO.setBankAddress("Nagercoil");

        List<BranchDTO> branchDTOList = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName("Main Nagercoil");
        branchDTO.setBranchAddress("Nagercoil");

        branchDTOList.add(branchDTO);

        bankDTO.setBranchDto(branchDTOList);

        when(bankService.updateBankDetails(any())).thenReturn(bankDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/banks")
                .content(asJsonString(bankDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void When_Deleting_usingInteger() throws Exception {

        when(bankService.deleteBank(anyInt())).thenReturn("Bank Details are deleted");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void When_Deleting_usingName() throws Exception {

        when(bankService.deleteBankByName(anyString())).thenReturn("Bank Details are deleted");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/banks/name/SBI")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    public String asJsonString(final Object obj) throws Exception {

            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;

    }
}
