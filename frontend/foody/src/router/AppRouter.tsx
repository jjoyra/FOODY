import React from 'react';
import 'styles/index.scss';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import HomePage from 'components/page/HomePage';
import MealPage from 'components/page/MealPage';
import RefriPage from 'components/page/RefriPage';
import RecommendPage from 'components/page/RecommendPage';
import DevPage from 'components/page/DevPage';
import Tabbar from 'components/organism/Tabbar/Tabbar';
import LoginPage from 'components/page/LoginPage';
import NotFoundPage from 'components/page/NotFoundPage';

function AppRouter() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Navigate replace to="/home" />} />
				<Route path="/home" element={<HomePage />} />
				<Route path="/meal" element={<MealPage />} />
				<Route path="/refri" element={<RefriPage />} />
				<Route path="/recommend" element={<RecommendPage />} />
				<Route path="/login" element={<LoginPage />} />

				<Route path="/dev" element={<DevPage />} />

				<Route path="/*" element={<Navigate replace to="/notfound" />} />
				<Route path="/notfound" element={<NotFoundPage />} />
			</Routes>

			<Tabbar />
		</BrowserRouter>
	);
}

export default AppRouter;
