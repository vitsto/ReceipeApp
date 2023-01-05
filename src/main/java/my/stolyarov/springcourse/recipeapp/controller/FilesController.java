package my.stolyarov.springcourse.recipeapp.controller;

import my.stolyarov.springcourse.recipeapp.service.FilesService;
import my.stolyarov.springcourse.recipeapp.service.IngredientService;
import my.stolyarov.springcourse.recipeapp.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public FilesController(FilesService filesService, RecipeService recipeService, IngredientService ingredientService) {
        this.filesService = filesService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/export/recipe")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File file = filesService.getDataFile(recipeService.getFileName());

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        return getVoidResponseEntity(file, recipeService.getFileName());
    }

    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile file) {
        return getVoidResponseEntity(file, ingredientService.getFileName());
    }

    private ResponseEntity<Void> getVoidResponseEntity(@RequestParam MultipartFile file, String fileName) {
        filesService.cleanDataFile(fileName);
        File dataFile = filesService.getDataFile(fileName);
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

}
