import streamlit as st
import pandas as pd
import joblib

# Load the trained model pipeline
model_pipeline = joblib.load('best_model_pipeline.pkl')  # Replace with your model file path

# Streamlit app
st.title('Car Price Prediction')

# Inputs for the user to change values before predicting
st.header("Enter the details of the vehicle:")
mileage = st.number_input('Mileage', min_value=0, max_value=300000, value=50000)
condition = st.selectbox('Condition', ['New', 'Used', 'Certified'])
seller_rating = st.number_input('Seller Rating', min_value=0.0, max_value=5.0, value=4.0)
seller_rating_count = st.number_input('Seller Rating Count', min_value=0, value=100)
year = st.number_input('Year', min_value=1980, max_value=2024, value=2020)
age = 2024 - year 

# Create input data DataFrame
input_data = pd.DataFrame({
    'Mileage': [mileage],
    'Condition': [condition],
    'Year': [year],
    'Age': [age],
    'Seller Rating': [seller_rating],
    'Seller Rating Count': [seller_rating_count]
})

# Ensure the condition categories match those used during training
input_data['Condition'] = input_data['Condition'].apply(lambda x: 'Certified' if 'Certified' in x else x)

# Print input data for debugging
st.write("Input Data:")
st.write(input_data)

# Button to make the prediction
if st.button('Predict'):
    # Predict using the entire pipeline
    try:
        prediction = model_pipeline.predict(input_data)
        st.write(f'Predicted Price: ${prediction[0]:,.2f}')
    except ValueError as e:
        st.error(f"Error during prediction: {e}")
