import java.util.List;

public class Order {
    private List<Item> items;
    private String customerName;
    private String customerEmail;
    private EmailSender emailSender;

    public Order(List<Item> items, String customerName, String customerEmail, EmailSender emailSender) {
        this.items = items;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.emailSender = emailSender;
    }

    private double calculateTotalPrice() {
        double total = 0.0;
        for (Item item : items) {
            total += applyItemDiscount(item) * item.getQuantity();
            if (item instanceof TaxableItem) {
                total += calculateTax(item);
            }
        }
        total = applyGiftCardDiscount(total);
        total = applyLargeOrderDiscount(total);
        return total;
    }
    
    private double applyItemDiscount(Item item) {
        double price = item.getPrice();
        switch (item.getDiscountType()) {
            case PERCENTAGE:
                return price - item.getDiscountAmount() * price;
            case AMOUNT:
                return price - item.getDiscountAmount();
            default:
                return price;
        }
    }
    
    private double calculateTax(Item item) {
        TaxableItem taxableItem = (TaxableItem) item;
        return taxableItem.getTaxRate() / 100.0 * item.getPrice();
    }
    
    private double applyGiftCardDiscount(double total) {
        if (hasGiftCard()) {
            total -= GIFT_CARD_DISCOUNT;
        }
        return total;
    }
    
    private double applyLargeOrderDiscount(double total) {
        if (total > LARGE_ORDER_THRESHOLD) {
            total *= LARGE_ORDER_DISCOUNT_RATE;
        }
        return total;
    }

    public void sendConfirmationEmail() {
        String message = "Thank you for your order, " + customerName + "!\n\n";
        message += "Your order details:\n";
        for (Item item : items) {
            message += item.getName() + " - " + item.getPrice() + "\n";
        }
        message += "Total: " + calculateTotalPrice();
        emailSender.sendEmail(customerEmail, "Order Confirmation", message);
    }


    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean hasGiftCard() {
        for (Item item : items) {
            if (item.isGiftCard()) {
                return true;
            }
        }
        return false;
    }

   public void printOrder() {
        System.out.println("Order Details:");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
   }

   public void addItemsFromAnotherOrder(Order otherOrder) {
        for (Item item : otherOrder.getItems()) {
            addItem(item);
        }
   }

}

