package ptixiaki;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jacop.constraints.XneqY;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;

/**
 *
 * @author Panos
 */
public class Panel3_Functions
{
    static void CSP(double PH,DefaultTableModel model2)
    {
        //No elements in shortage
        if (Lists.ShortageList.isEmpty())
            return;
        System.out.println("\nSTARTING CSP:");
        ArrayList<String> compoundList = new ArrayList<>();
        //First element is the key element and the following ones are those who have constraint with the key element
        Map<String,List<String>> constraintsDict = new HashMap<>();
        
        //For each element create constraintsList and add it to Dictionary
        //ΑΣΒΕΣΤΙΟ
        constraintsDict.put(ΟΞΕΙΔΙΟ_ΑΣΒΕΣΤΙΟΥ, Arrays.asList(""));
        constraintsDict.put(ΝΙΤΡΙΚΟ_ΑΣΒΕΣΤΙΟ, Arrays.asList(ΦΩΣΦΟΡΙΚΟ_ΟΞΥ, ΣΙΔΗΡΟΣ, ΧΑΛΚΟΣ, ΨΕΥΔΑΡΓΥΡΟΣ, ΜΑΓΓΑΝΙΟ));
        //ΦΩΣΦΟΡΟΣ
        constraintsDict.put(ΦΩΣΦΟΡΙΚΟ_ΑΜΜΩΝΙΟ, Arrays.asList(ΘΕΙΙΚΟΣ_ΣΙΔΗΡΟΣ, ΘΕΙΙΚΟΣ_ΧΑΛΚΟΣ, ΘΕΙΙΚΟΣ_ΨΕΥΔΑΡΓΥΡΟΣ, ΘΕΙΙΚΟ_ΜΑΓΓΑΝΙΟ, ΘΕΙΙΚΟ_ΜΑΓΝΗΣΙΟ));
        constraintsDict.put(ΦΩΣΦΟΡΙΚΟ_ΟΞΥ, Arrays.asList(ΣΙΔΗΡΟΣ, ΧΑΛΚΟΣ, ΨΕΥΔΑΡΓΥΡΟΣ, ΜΑΓΓΑΝΙΟ));
        //ΚΑΛΙΟ
        constraintsDict.put(ΘΕΙΙΚΟ_ΚΑΛΙΟ, Arrays.asList(ΘΕΙΙΚΟΣ_ΣΙΔΗΡΟΣ, ΘΕΙΙΚΟΣ_ΧΑΛΚΟΣ, ΘΕΙΙΚΟΣ_ΨΕΥΔΑΡΓΥΡΟΣ, ΘΕΙΙΚΟ_ΜΑΓΓΑΝΙΟ, ΘΕΙΙΚΟ_ΜΑΓΝΗΣΙΟ, ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ));
        constraintsDict.put(ΝΙΤΡΙΚΟ_ΚΑΛΙΟ, Arrays.asList(""));
        //ΒΟΡΙΟ
        constraintsDict.put(ΒΟΡΙΚΟ_ΟΞΥ, Arrays.asList(""));
        constraintsDict.put(ΒΟΡΑΚΑΣ, Arrays.asList(""));
        //ΣΙΔΗΡΟΣ
        constraintsDict.put(ΘΕΕΙΚΟΣ_ΣΙΔΗΡΟΣ, Arrays.asList(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ));
        constraintsDict.put(ΣΙΔΗΡΟΣ, Arrays.asList(""));
        //ΧΑΛΚΟΣ
        constraintsDict.put(ΘΕΕΙΚΟΣ_ΧΑΛΚΟΣ, Arrays.asList(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ));
        constraintsDict.put(ΧΑΛΚΟΣ, Arrays.asList(""));
        //ΨΕΥΔΑΡΓΥΡΟΣ
        constraintsDict.put(ΘΕΕΙΚΟΣ_ΨΕΥΔΑΡΓΥΡΟΣ, Arrays.asList(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ));
        constraintsDict.put(ΨΕΥΔΑΡΓΥΡΟΣ, Arrays.asList(""));
        //ΜΑΓΓΑΝΙΟ
        constraintsDict.put(ΘΕΕΙΚΟ_ΜΑΓΓΑΝΙΟ, Arrays.asList(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ));
        constraintsDict.put(ΜΑΓΓΑΝΙΟ, Arrays.asList(""));
        //ΜΑΓΝΗΣΙΟ
        constraintsDict.put(ΘΕΕΙΚΟ_ΜΑΓΝΗΣΙΟ, Arrays.asList(""));
        constraintsDict.put(ΝΙΤΡΙΚΟ_ΜΑΓΝΗΣΙΟ, Arrays.asList(""));        
        //ΑΖΩΤΟ     
        constraintsDict.put(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ, Arrays.asList(""));
        constraintsDict.put(ΝΙΤΡΙΚΟ_ΑΜΜΩΝΙΟ, Arrays.asList(""));
               
        
        int size = Lists.ShortageList.size();
        
        // PH > 7, USE ACIDS
        if (PH > 7)
        {
            for (int i=0; i<size; i++)
            {
                if (Lists.ShortageList.get(i).contains(ΑΣΒΕΣΤΙΟ))
                    compoundList.add(ΟΞΕΙΔΙΟ_ΑΣΒΕΣΤΙΟΥ);
                else if (Lists.ShortageList.get(i).contains(ΦΩΣΦΟΡΟΣ))
                    compoundList.add(ΦΩΣΦΟΡΙΚΟ_ΑΜΜΩΝΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΚΑΛΙΟ))
                    compoundList.add(ΘΕΙΙΚΟ_ΚΑΛΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΒΟΡΙΟ))
                    compoundList.add(ΒΟΡΙΚΟ_ΟΞΥ);
                else if (Lists.ShortageList.get(i).contains(ΣΙΔΗΡΟΣ))
                    compoundList.add(ΘΕΕΙΚΟΣ_ΣΙΔΗΡΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΧΑΛΚΟΣ))
                    compoundList.add(ΘΕΕΙΚΟΣ_ΧΑΛΚΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΨΕΥΔΑΡΓΥΡΟΣ))
                    compoundList.add(ΘΕΕΙΚΟΣ_ΨΕΥΔΑΡΓΥΡΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΜΑΓΓΑΝΙΟ))
                    compoundList.add(ΘΕΕΙΚΟ_ΜΑΓΓΑΝΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΜΑΓΝΗΣΙΟ))
                    compoundList.add(ΘΕΕΙΚΟ_ΜΑΓΝΗΣΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΑΖΩΤΟ))
                    compoundList.add(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ);            
            }
        }
        // PH <= 7, USE ALKALI
        else
        {    
            for (int i=0; i<size; i++)
            {
                if (Lists.ShortageList.get(i).contains(ΑΣΒΕΣΤΙΟ))
                    compoundList.add(ΝΙΤΡΙΚΟ_ΑΣΒΕΣΤΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΦΩΣΦΟΡΟΣ))
                    compoundList.add(ΦΩΣΦΟΡΙΚΟ_ΟΞΥ);
                else if (Lists.ShortageList.get(i).contains(ΚΑΛΙΟ))
                    compoundList.add(ΝΙΤΡΙΚΟ_ΚΑΛΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΒΟΡΙΟ))
                    compoundList.add(ΒΟΡΑΚΑΣ);
                else if (Lists.ShortageList.get(i).contains(ΣΙΔΗΡΟΣ))
                    compoundList.add(ΣΙΔΗΡΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΧΑΛΚΟΣ))
                    compoundList.add(ΧΑΛΚΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΨΕΥΔΑΡΓΥΡΟΣ))
                    compoundList.add(ΨΕΥΔΑΡΓΥΡΟΣ);
                else if (Lists.ShortageList.get(i).contains(ΜΑΓΓΑΝΙΟ))
                    compoundList.add(ΜΑΓΓΑΝΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΜΑΓΝΗΣΙΟ))
                    compoundList.add(ΝΙΤΡΙΚΟ_ΜΑΓΝΗΣΙΟ);
                else if (Lists.ShortageList.get(i).contains(ΑΖΩΤΟ))
                    compoundList.add(ΝΙΤΡΙΚΟ_ΑΜΜΩΝΙΟ);            
            }
        }
                
        //Copy elements to the static List
        Lists.CompoundList = new ArrayList<>(compoundList);
        
        
        //JaCoP library functions to initialiaze and solve CSP
        
        Store store = new Store();
        
        IntVar[] v = new IntVar[size];
        
        //start from 1 to size(included) because we need value 0 as default domain for all variables
        for (int i=1; i<=size; i++)
        {
            v[i-1] = new IntVar(store,compoundList.get(i-1),0,0);
            v[i-1].addDom(i,i); //add one unique number for each element in case value '0' is not compatible given the constraints
        }
        //Form constraints
        for (int i=0; i<size; i++)
        {
            for (int j=0; j<size; j++)
            {
                if (i==j) continue;
                if (constraintsDict.get(compoundList.get(i)).contains(compoundList.get(j)))
                    store.impose(new XneqY(v[i],v[j]));  
            }
        }
        
        //Search for solution using DFS
        Search<IntVar> search = new DepthFirstSearch<>();
        
        //Selection order of values in domain, start from Min
        SelectChoicePoint<IntVar> select = new InputOrderSelect<>(store, v, new IndomainMin<>());
        
        //result
        boolean result = search.labeling(store, select);
        

        if (result)
        {            
            //Pass results to FertilizationMethod list
            for (int i=0; i<size; i++)
                Lists.resultList.add(v[i].toString());
        }        
        
        System.out.println("\nCSP COMPLETED");
    }    

    static void FertilizationMethod()
    {        
        if (Lists.resultList.isEmpty()) return;
        
        Lists.FertilizationMethod = new ArrayList<>();
        
        for (String result : Lists.resultList)
        {
            if (result.split("=")[1].equals(" 0")) //result has a space after =, for example v[1] = 0, 
               Lists.FertilizationMethod.add("ΣΤΟ ΕΔΑΦΟΣ");
            else
               Lists.FertilizationMethod.add("ΔΙΑΦΥΛΛΙΚΑ ΜΕ ΨΕΚΑΣΜΟ"); 
        }
    }

    static void MolecularWeightCalculation(double PH)
    {
        if (Lists.resultList.isEmpty()) return;
        
        
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMaximumFractionDigits(3);         
        Lists.MolecularWeightList = new ArrayList<>();
        Lists.RestMolecularWeightList = new ArrayList<>();
        
        
       if (PH > 7)
        {
            for (int i=0; i<Lists.resultList.size(); i++)
            {
                if (Lists.resultList.get(i).contains(ΟΞΕΙΔΙΟ_ΑΣΒΕΣΤΙΟΥ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(1.4 * Lists.NewQuantityList.get(i)))); // 56/40
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(1.4 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΦΩΣΦΟΡΙΚΟ_ΑΜΜΩΝΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(4.806 * Lists.NewQuantityList.get(i)))); // 149/31
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(4.806 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΙΙΚΟ_ΚΑΛΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.230 * Lists.NewQuantityList.get(i)))); // 174/78
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.230 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΒΟΡΙΚΟ_ΟΞΥ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(5.636 * Lists.NewQuantityList.get(i)))); // 62/11
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(5.636 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟΣ_ΣΙΔΗΡΟΣ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.714 * Lists.NewQuantityList.get(i)))); // 152/56
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.714 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟΣ_ΧΑΛΚΟΣ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.511 * Lists.NewQuantityList.get(i)))); // 159.5/63.5
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.511 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟΣ_ΨΕΥΔΑΡΓΥΡΟΣ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.476 * Lists.NewQuantityList.get(i)))); // 161/65
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.476 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟ_ΜΑΓΓΑΝΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.745 * Lists.NewQuantityList.get(i)))); // 151/55
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.745 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟ_ΜΑΓΝΗΣΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(5 * Lists.NewQuantityList.get(i)))); // 120/24
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(5 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΘΕΕΙΚΟ_ΑΜΜΩΝΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(4.714 * Lists.NewQuantityList.get(i)))); // 132/28 
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(4.714 * Lists.RestList.get(i)))); // 56/40
                }
            }
        }
        // PH <= 7, USE ALKALI
        else
        {    
            for (int i=0; i<Lists.resultList.size(); i++)
            {
                if (Lists.resultList.get(i).contains(ΝΙΤΡΙΚΟ_ΑΣΒΕΣΤΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(5.65 * Lists.NewQuantityList.get(i)))); // 226/40
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(5.65 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΦΩΣΦΟΡΙΚΟ_ΟΞΥ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(3.161 * Lists.NewQuantityList.get(i)))); // 98/31
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(3.161 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΝΙΤΡΙΚΟ_ΚΑΛΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.589 * Lists.NewQuantityList.get(i)))); // 101/39
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.589 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΒΟΡΑΚΑΣ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(5.409 * Lists.NewQuantityList.get(i)))); // 238/44
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(5.409 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΣΙΔΗΡΟΣ))
                {
                    Lists.MolecularWeightList.add(Lists.NewQuantityList.get(i)); 
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΧΑΛΚΟΣ))
                {
                    Lists.MolecularWeightList.add(Lists.NewQuantityList.get(i)); 
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΨΕΥΔΑΡΓΥΡΟΣ))
                {
                    Lists.MolecularWeightList.add(Lists.NewQuantityList.get(i));
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΜΑΓΓΑΝΙΟ))
                {
                    Lists.MolecularWeightList.add(Lists.NewQuantityList.get(i));
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΝΙΤΡΙΚΟ_ΜΑΓΝΗΣΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(6.166 * Lists.NewQuantityList.get(i)))); // 148/24
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(6.166 * Lists.RestList.get(i)))); // 56/40
                }
                else if (Lists.resultList.get(i).contains(ΝΙΤΡΙΚΟ_ΑΜΜΩΝΙΟ))
                {
                    Lists.MolecularWeightList.add(Double.parseDouble(nf.format(2.857 * Lists.NewQuantityList.get(i)))); // 80/28  
                    Lists.RestMolecularWeightList.add(Double.parseDouble(nf.format(2.857 * Lists.RestList.get(i)))); // 56/40
                }
            }
        }        
    }
    
}
