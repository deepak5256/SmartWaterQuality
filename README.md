
---



##  Water Quality Prediction

This project combines **machine learning** and a **Java GUI** to create an application that predicts water potability based on various water quality parameters. 

### **Project Structure**
1. **water_quality_model.pkl**: Pre-trained machine learning model for water quality prediction.
2. **scaler.pkl**: Scaling logic for normalizing input data.
3. **water_potability.csv**: Dataset containing water quality parameters like pH, Hardness, etc.
4. **train_model.py**: Python script to train the machine learning model.
5. **predict.py**: Python script for making batch predictions using the trained model.
6. **predict_water_quality.py**: Python script to streamline the prediction process, possibly used for CLI.
7. **background.jpg**: Background image used for the GUI.
8. **WaterQualityGUI.java**: Java Swing-based GUI for inputting water parameters and receiving predictions.
9. **Compiled Classes**: 
   - **WaterQualityGUI.class**: Compiled Java GUI classes.

### **Features**
- **Machine Learning**: Predicts water potability based on key parameters.
- **Interactive GUI**: User-friendly Java-based interface for input and output.
- **Python Integration**: Model training and predictions handled in Python, integrated with Java.

### **How to Run**
1. **Python Setup**:
   - Install dependencies:
     ```bash
     pip install pandas scikit-learn
     ```
   - Train the model:
     ```bash
     python train_model.py
     ```
   - Test predictions:
     ```bash
     python predict.py
     ```

2. **Java Setup**:
   - Compile the GUI:
     ```bash
     javac WaterQualityGUI.java
     ```
   - Run the GUI:
     ```bash
     java WaterQualityGUI
     ```

3. **Ensure Integration**:
   - Verify Python and Java paths for seamless interaction.

---
