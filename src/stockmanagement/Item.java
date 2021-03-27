/*
 * The MIT License
 *
 * Copyright 2021 gfoster.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package stockmanagement;

/**
 *
 * @author gfoster
 */
public class Item implements Comparable<Item>{
    static final String DELIMITER = "#%#";

    private String name;
    private String code;
    private String details;
    private int price;
    private boolean taxExempt;
    
    
    Item (String name, String code, String details, int price, boolean taxExempt){
        this.name = name;
        this.code = code;
        this.details = details;
        this.price = price;
        this.taxExempt = taxExempt;
    }

    @Override
    public String toString(){
        return code + " " + name + " (" + price + ")";
    }
    
    @Override
    public int compareTo(Item item) {
        return (this.code.compareTo(item.code));
    }
    
    public String saleDisplay(int quantity){
        return String.format("%20s %3d at %3d = %5d",name, quantity, price, (quantity* price));
    }
    
    public String fileDetails(){
        return name + DELIMITER + code  + DELIMITER + details + DELIMITER + price + DELIMITER + taxExempt;
    }
    
    public String getCode() { return code;}
    public int getPrice() { return price;}
} // end of class Item
