import sys
import pandas as pd
import pickle
from sklearn.preprocessing import StandardScaler

# Load the trained model
with open('water_quality_model.pkl', 'rb') as file:
    model = pickle.load(file)

# Load the scaler used for training
scaler = StandardScaler()
scaler.mean_ = [7.0, 200.0, 100.0, 10.0, 5.0, 500.0, 5.0, 0.5, 10.0]  # Example means
scaler.scale_ = [1.0, 50.0, 10.0, 2.0, 1.0, 100.0, 2.0, 0.1, 2.0]  # Example scales

def predict_water_quality(ph, hardness, solids, chloramines, sulfate, conductivity, organic_carbon, trihalomethanes, turbidity):
    # Prepare the input data
    input_data = pd.DataFrame([[ph, hardness, solids, chloramines, sulfate, conductivity, organic_carbon, trihalomethanes, turbidity]],
                              columns=['ph', 'Hardness', 'Solids', 'Chloramines', 'Sulfate', 'Conductivity', 'Organic_carbon', 'Trihalomethanes', 'Turbidity'])
    
    # Scale the input data
    input_data_scaled = scaler.transform(input_data)
    
    # Make a prediction
    prediction = model.predict(input_data_scaled)
    
    # Return the prediction
    return prediction[0]

if __name__ == "__main__":
    # Get input arguments
    ph = float(sys.argv[1])
    hardness = float(sys.argv[2])
    solids = float(sys.argv[3])
    chloramines = float(sys.argv[4])
    sulfate = float(sys.argv[5])
    conductivity = float(sys.argv[6])
    organic_carbon = float(sys.argv[7])
    trihalomethanes = float(sys.argv[8])
    turbidity = float(sys.argv[9])
    
    # Predict water quality
    result = predict_water_quality(ph, hardness, solids, chloramines, sulfate, conductivity, organic_carbon, trihalomethanes, turbidity)
    
    # Print the result (this will be captured by Java)
    print(result)

