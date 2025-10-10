import { useEffect, useState } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';
import Alert from 'react-bootstrap/Alert';

type Book = {
  id: number;
  title: string;
  author: string;
  available?: boolean;      
  createdByUsername?: string;  
};

export default function BookList() {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchBooks = async () => {
    try {
      setLoading(true);
      setError(null);
      const res = await axios.get<Book[]>('/api/books');
      setBooks(res.data ?? []);
    } catch (err) {
      console.error(err);
      setError('Failed to load books.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  if (loading) {
    return (
      <div className="d-flex align-items-center gap-2">
        <Spinner animation="border" size="sm" />
        <span>Loading books...</span>
      </div>
    );
  }

  if (error) {
    return (
      <Alert variant="danger" className="d-flex align-items-center justify-content-between">
        <span>{error}</span>
        <Button variant="outline-light" size="sm" onClick={fetchBooks}>
          Retry
        </Button>
      </Alert>
    );
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-2">
        <h5 className="m-0">Books</h5>
        <Button variant="secondary" size="sm" onClick={fetchBooks}>
          Refresh
        </Button>
      </div>

      <Table striped bordered hover responsive size="sm">
        <thead>
          <tr>
            <th style={{width: 80}}>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th style={{width: 120}}>Available</th>
          </tr>
        </thead>
        <tbody>
          {books.length === 0 ? (
            <tr>
              <td colSpan={4} className="text-center text-muted">
                No books found.
              </td>
            </tr>
          ) : (
            books.map(b => (
              <tr key={b.id}>
                <td>{b.id}</td>
                <td>{b.title}</td>
                <td>{b.author}</td>
                <td>{b.available === undefined ? '-' : (b.available ? 'Yes' : 'No')}</td>
              </tr>
            ))
          )}
        </tbody>
      </Table>
    </div>
  );
}
