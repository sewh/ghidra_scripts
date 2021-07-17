//@author sewh
//@category Strings


import ghidra.app.script.GhidraScript;
import ghidra.program.model.mem.*;
import ghidra.program.model.lang.*;
import ghidra.program.model.pcode.*;
import ghidra.program.model.util.*;
import ghidra.program.model.reloc.*;
import ghidra.program.model.data.*;
import ghidra.program.model.block.*;
import ghidra.program.model.symbol.*;
import ghidra.program.model.scalar.*;
import ghidra.program.model.listing.*;
import ghidra.program.model.address.*;
import ghidra.program.util.DefinedDataIterator;
import util.CollectionUtils;

public class StringsInFunction extends GhidraScript {

    public void run() throws Exception {
    	// get the current function
    	Function func = currentProgram
    			.getFunctionManager()
    			.getFunctionContaining(currentAddress);
    	ReferenceManager refMgr = currentProgram.getReferenceManager();
    	AddressSetView addrSetView = func.getBody();
    	AddressRange addrRange = addrSetView.iterator().next();
    	
    	println("Strings in function: " + func.getName());
    	for (Data data : CollectionUtils.asIterable(
    			DefinedDataIterator.definedStrings(currentProgram))) {
    		for (Reference ref : data.getReferenceIteratorTo()) {
    			Address addr = ref.getFromAddress();
    			if (addrRange.contains(addr)) {
    	    		StringDataInstance str = StringDataInstance.getStringDataInstance(data);
    				String s = str.getStringValue();
    				if (s != null) {
    					println("From: " + addr.toString() + " To: " + ref.getToAddress() + " \"" + s + "\"");
    				}
    			}
    		}
    	}
    }
}
