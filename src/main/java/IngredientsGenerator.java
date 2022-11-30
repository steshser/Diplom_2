import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IngredientsGenerator {

    public static Ingredients getValidIngredientsHash(ArrayList<HashMap<String, String>> ingredientsList){
        ArrayList<String> ingredientsHash = new ArrayList<>();
        for (int ingredient=0; ingredient < ingredientsList.size(); ingredient += 1){
            ingredientsHash.add(ingredientsList.get(ingredient).get("_id"));
        }
        return new Ingredients(ingredientsHash);
    }

    public static Ingredients getInvalidIngredientsHash(){
        ArrayList<String> ingredientsInvalidHash = new ArrayList<>();
        byte[] array = new byte[24]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedHash = new String(array, StandardCharsets.UTF_8);
        ingredientsInvalidHash.add(generatedHash);
        return new Ingredients(ingredientsInvalidHash);
    }
}
