package org.fs.bsc.flow.editor.selection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectionManager {

    private final List<Selectable> selections = new ArrayList<>();

    public boolean selectOne(Selectable selectable) {
        if (!selectable.selected()) {
            return false;
        }
        cleanSelection();
        selections.add(selectable);
        return true;
    }

    public boolean selectSome(List<Selectable> selections) {
        if(null == selections || selections.isEmpty()){
            return false;
        }
        List<Selectable> ableSelections = new ArrayList<>();
        for(Selectable selectable: selections){
            if(selectable.selected()){
                ableSelections.add(selectable);
            }
        }
        if(ableSelections.isEmpty()){
            return false;
        }
        cleanSelection();
        this.selections.addAll(selections);
        return true;
    }

    public boolean addSelection(Selectable selectable){
        if(selectable.selected()){
            selections.add(selectable);
            return true;
        }
        return false;
    }

    public boolean removeSelection(Selectable selectable){
        Iterator<Selectable> iterable = selections.iterator();
        while(iterable.hasNext()){
            Selectable tmpSelect = iterable.next();
            if(tmpSelect.equals(selectable)){
                selectable.deselected();
                iterable.remove();
                return true;
            }
        }
        return false;
    }

    public boolean cleanSelection(){
        for(Selectable tmpSelect: selections){
            tmpSelect.deselected();
        }
        selections.clear();
        return true;
    }

    public List<Selectable> getSelections() {
        return selections;
    }

    public boolean hasSelection(){
        return !selections.isEmpty();
    }
}
