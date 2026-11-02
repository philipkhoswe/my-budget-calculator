import tkinter as tk
from tkinter import messagebox

def calculate_budget():
    try:
        # Get values from the text boxes
        income = float(entry_income.get())
        expenses = float(entry_expenses.get())
        
        # Calculate
        balance = income - expenses
        
        # Display result
        if balance > 0:
            result_label.config(text=f"Remaining Balance: MWK {balance:,.2f}\n(You saved money!)", fg="green")
        elif balance == 0:
            result_label.config(text="Remaining Balance: MWK 0.00\n(Exactly broke even)", fg="black")
        else:
            result_label.config(text=f"Remaining Balance: MWK {balance:,.2f}\n(You overspent!)", fg="red")
            
    except ValueError:
        messagebox.showerror("Error", "Please enter numbers only!")

# --- Setup the Main Window ---
root = tk.Tk()
root.title("My Budget App")
root.geometry("300x350")

# --- Income ---
tk.Label(root, text="Total Income (MWK):").pack(pady=5)
entry_income = tk.Entry(root)
entry_income.pack(pady=5)

# --- Expenses ---
tk.Label(root, text="Total Expenses (MWK):").pack(pady=5)
entry_expenses = tk.Entry(root)
entry_expenses.pack(pady=5)

# --- Calculate Button ---
calculate_btn = tk.Button(root, text="Calculate Balance", command=calculate_budget, bg="blue", fg="white")
calculate_btn.pack(pady=15)

# --- Result Display ---
result_label = tk.Label(root, text="Enter numbers above", font=("Arial", 11, "bold"))
result_label.pack(pady=10)

# Run the app
root.mainloop()