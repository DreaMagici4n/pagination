/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pagination.Controller.algoritimosSubstituicao;

import java.util.ArrayList;
import java.util.List;
import pagination.Controller.Page;
import pagination.Controller.Ram;

/**
 *
 * @author lucas
 */
public class Otimo extends Base {
    private List<Integer> distanceToAcess;

    /**
     * Algoritimo de Substituicao OPT(Otimo)
     *
     * @param sequencia sequecia de acessos a paginas na memoria
     * @param pages     paginas a serem carregadas na memoria
     * @param ram       memoria a ser carregada
     */
    public Otimo(List<Integer> sequencia, List<Page> pages, Ram ram) {
        super(sequencia, pages, ram);
        
        this.init();
        
        this.distanceToAcess = new ArrayList<>(sequencia);
    }
    
    @Override
    protected boolean check(int id) {
        if(!this.distanceToAcess.isEmpty()) {
            this.distanceToAcess.remove(0);
        }
        
        for (int i = 0; i < this.ram.getTamanhoMoldura(); i++) {
            Page page = this.molduras.get(i);

            if (page.getId() == id) {
                this.containMessage(id);
                page.getAccess();
                return true;
            }
        }
        this.notContainMessage(id);

        
        return false;
    }

    @Override
    protected void change(int id) {
        int maisTempoSemAcesso = this.ram.getMaisTempoSemAcesso(this.distanceToAcess);

        this.removeMessage(this.molduras.get(maisTempoSemAcesso).getId());

        for (Page page : this.pages) {
            if (page.getId() == id) {
                this.faltas++;
                page.getAccess();
                page.setIdade(System.nanoTime());
                this.molduras.set(maisTempoSemAcesso, page);
                this.addMessage(page.getId());         
                break;
            }
        }
        this.ram.setMolduras(this.molduras);
    }
}
