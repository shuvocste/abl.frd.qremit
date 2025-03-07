package abl.frd.qremit.converter.nafex.controller;
import abl.frd.qremit.converter.nafex.helper.NafexModelServiceHelper;
import abl.frd.qremit.converter.nafex.model.FileInfoModel;
import abl.frd.qremit.converter.nafex.service.NafexModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/qremit")
public class NafexEhMstModelController {
    private final NafexModelService nafexModelService;

    @Autowired
    public NafexEhMstModelController(NafexModelService nafexModelService){
        this.nafexModelService = nafexModelService;
    }

    @GetMapping(value = "/index")
    public String homePage() {
        return "homePage";
    }
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        String message = "";
        String count ="";
        FileInfoModel fileInfoModelObject;
        if (NafexModelServiceHelper.hasCSVFormat(file)) {
            int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
            try {
                fileInfoModelObject = nafexModelService.save(file);
                model.addAttribute("fileInfo", fileInfoModelObject);
                return "downloadPage";

            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return "downloadPage";
            }
        }
        message = "Please upload a csv file!";
        return "downloadPage";
    }

}
