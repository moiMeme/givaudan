import { User } from './user';
export interface AuthState {
  user: User | null;
}

export const initialState: AuthState = {
  user: null,
};
