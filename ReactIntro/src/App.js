import {useEffect, useState} from 'react';
import './App.css';
import SearchIcon from './search.svg'
import MovieCard from './MovieCard';

const API_URL = 'http://www.omdbapi.com?apikey=e9799c8f';


const App = () => {

    const [movies, setMovies] = useState ([]);
    const [searchTerm, setSearchTerm] = useState('');
    const handleKeyDown = (e) => {
        if (e.key === "Enter"){
            serachMovies(searchTerm)
        }

    }

    const serachMovies = async (title) => {
        const response = await fetch(`${API_URL}&s=${title}`);
        const data = await response.json();

        setMovies(data.Search);

    }

    useEffect(() =>{
        serachMovies('Batman');


    }, []);
    
    return(
        <div className='app'>
            <h1>MovieLand</h1>

            <div className='search'>
                <input
                    placeholder='Search for movies'
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    onKeyDown={handleKeyDown}
                />
                <img
                    src = {SearchIcon}
                    alt ="search"
                    onClick = {() => serachMovies(searchTerm)}
                />

            </div>

            {movies?.length > 0
                 ?(
                <div className='container'>
                    {movies.map((movie) => 
                        <MovieCard movie = {movie}/>
                    )}
    
                    </div>
                ): (

                    <div className='empty'>
                        <h2>No movies found</h2>
                    </div>

                )}
        </div>
    );
}

export default App;