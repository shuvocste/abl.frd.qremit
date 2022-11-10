package abl.frd.qremit.converter.nafex.service;

import abl.frd.qremit.converter.nafex.helper.NafexModelServiceHelper;
import abl.frd.qremit.converter.nafex.model.NafexEhMstModel;
import abl.frd.qremit.converter.nafex.repository.NafexModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class NafexModelService {
    @Autowired
    NafexModelRepository nafexModelRepository;
    public String save(MultipartFile file) {
        String numberOfRows=null;
        try
        {
            List<NafexEhMstModel> nafexModels = NafexModelServiceHelper.csvToNafexModels(file.getInputStream());
            for(NafexEhMstModel nafexModel : nafexModels){
                nafexModel.setExchangeCode("7010234");
            }

            Map<String, List<NafexEhMstModel>> listOfModelHavingOnlineAccount = NafexModelServiceHelper.segregateDifferentTypesOfModel(nafexModels);
            System.out.println("........"+listOfModelHavingOnlineAccount);
            nafexModelRepository.saveAll(nafexModels);

            numberOfRows = String.valueOf(nafexModelRepository.count());
            return numberOfRows;
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
}