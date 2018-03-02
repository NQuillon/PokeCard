package nicolas.johan.iem.pokecard.webservice;

import java.util.List;

import nicolas.johan.iem.pokecard.pojo.Pokemon;

/**
 * Created by iem on 02/03/2018.
 */

public interface getUserPokemonInterface {
    void refresh(List<Pokemon> list);
    void onNoPokemon();
}
