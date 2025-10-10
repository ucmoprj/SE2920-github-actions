import './App.css'
import { Route, Routes } from 'react-router-dom'
import TopNav from './componets/TopNav'
import BookList from './pages/BookList'
import BookCreate from './pages/BookCreate'

function App() {
 
  return (
    <>
      <TopNav />
      <div className="container mt-4">
        <Routes>
          <Route path="/booklist" element={<BookList />} />
          <Route path="/bookcreate" element={<BookCreate />} />
        </Routes>
      </div>
    </>
  )
}

export default App
